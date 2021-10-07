package cn.hfbin.ucpm.service.impl;

import cn.hfbin.ucpm.entity.*;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.enums.DsTypeEnum;
import cn.hfbin.ucpm.enums.RelationTypeEnum;
import cn.hfbin.ucpm.mapper.IdentityDepartRefMapper;
import cn.hfbin.ucpm.params.InitTenantParams;
import cn.hfbin.ucpm.params.RoleMenuParams;
import cn.hfbin.ucpm.params.RoleParams;
import cn.hfbin.ucpm.vo.InitTenantVo;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.ucpm.service.*;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: huangfubin
 * @Description: InitTenantServiceImpl 类
 * @Date: 2021/8/20
 */
@Service
public class InitTenantServiceImpl implements InitTenantService {

    @Autowired
    private DepartService departService;
    @Autowired
    private RoleGroupService roleGroupService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IdentityEmployeeService identityEmployeeService;
    @Autowired
    private IdentityDepartRefMapper identityDepartRefMapper;
    @Autowired
    private RelationRoleService relationRoleService;


    /**
     * 1、创建默认部门
     * 2、创建角色及角色组
     * 3、将租户对应菜单模版的权限赋予管理员角色
     * 4、创建用户（账号及身份信息），绑定部门、角色等
     * @param params
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InitTenantVo initTenant(InitTenantParams params) {
        String tenantCode = params.getTenantCode();
        // 1、创建默认部门
        Depart depart = new Depart();
        depart.setDeptName(params.getTenantName());
        depart.setTenantCode(tenantCode);
        departService.insertIgnoreTr(depart);

        // 2、新增角色组
        RoleGroup roleGroup = new RoleGroup();
        roleGroup.setName("系统默认");
        roleGroup.setSystemDefault(1);
        roleGroup.setTenantCode(tenantCode);
        roleGroupService.insert(roleGroup);

        // 2、新增角色
        Role role = new Role();
        role.setRoleName("管理员");
        role.setTenantCode(tenantCode);
        role.setGroupId(roleGroup.getId());
        role.setRoleCode("ADMIN");
        role.setDsType(DsTypeEnum.ALL.getCode());
        role.setSystemDefault(1);
        roleService.insert(role);

        // 2、新增角色菜单关联关系
        RoleMenuParams roleMenuParams = new RoleMenuParams();
        roleMenuParams.setRoleId(role.getId());
        roleMenuParams.setMenuIds(params.getMenuIds());
        roleMenuParams.setTenantCode(tenantCode);
        roleService.saveRoleMenuRef(roleMenuParams);

        // 3、创建账号
        Account account = new Account();
        account.setUsername(params.getUsername());
        account.setPassword(params.getPassword());
        account.setMobile(params.getMobile());
        account.setTenantCode(tenantCode);
        accountService.insert(account);

        IdentityEmployee employee = new IdentityEmployee();
        employee.setAccountId(account.getId());
        employee.setBirthday(LocalDateTime.now());
        employee.setEntryDate(LocalDateTime.now());
        employee.setTenantCode(tenantCode);
        employee.setAvatar("http://img.jj20.com/up/allimg/tx20/12041101541773.jpg");
        employee.setName("管理员");
        employee.setMobile(params.getMobile());
        employee.setWorkNo("000000");
        employee.setSystemDefault(1);
        identityEmployeeService.saveIgnoreTr(employee);

        // 保存角色关联关系
        RelationRole relationRole = new RelationRole();
        relationRole.setRoleId(role.getId());
        relationRole.setRelationId(employee.getId());
        relationRole.setRelationType(RelationTypeEnum.USER_ID.getCode());
        relationRole.setTenantCode(tenantCode);
        relationRole.setDelFlag(0);
        if(relationRoleService.insert(relationRole) < 1) throw new LibraException(UcPmExceptionCode.FAILED_TO_ADD_ASSOCIATION);

        // 保存部门关联关系
        IdentityDepartRef identityDepartRef = new IdentityDepartRef();
        identityDepartRef.setDeptId(depart.getId());
        identityDepartRef.setTenantCode(tenantCode);
        identityDepartRef.setIdentityId(employee.getId());

        // 主部门类型
        identityDepartRef.setDeptType(1);
        if(identityDepartRefMapper.insert(identityDepartRef) < 1) throw new LibraException(UcPmExceptionCode.FAILED_TO_ADD_ASSOCIATION);

        InitTenantVo initTenantVo = new InitTenantVo();
        initTenantVo.setIdentityId(employee.getId());
        return initTenantVo;
    }


    /**
     * 同步租户默认管理员角色权限
     * @param params
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncAdminRoleMenuPermission(InitTenantParams params) {
        if(Objects.isNull(params) || CollectionUtil.isEmpty(params.getTenantCodes()) || CollectionUtil.isEmpty(params.getMenuIds())){
            return 0;
        }
        RoleParams roleParams = new RoleParams();
        roleParams.setTenantCodes(params.getTenantCodes());
        roleParams.setSystemDefault(1);
        List<Role> roles = roleService.selectListIgnoreTr(roleParams);
        // 删除原有关联数据
        RoleMenuParams roleMenu = new RoleMenuParams();
        roleMenu.setTenantCodes(params.getTenantCodes());
        roleMenu.setRoleIds(roles.stream().map(Role::getId).collect(Collectors.toList()));
        roleService.deleteRoleMenuRefIgnoreTr(roleMenu);

        // 按角色循环去插入
        roles.forEach(data -> {
            // 新增角色菜单关联关系
            RoleMenuParams roleMenuParams = new RoleMenuParams();
            roleMenuParams.setRoleId(data.getId());
            roleMenuParams.setMenuIds(params.getMenuIds());
            roleMenuParams.setTenantCode(data.getTenantCode());
            roleService.saveRoleMenuRef(roleMenuParams);
        });
        return roles.size();
    }
}
