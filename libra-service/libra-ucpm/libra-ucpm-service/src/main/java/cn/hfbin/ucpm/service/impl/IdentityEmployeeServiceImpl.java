/*
 *    Copyright [2021] [LibraPlatform of copyright huangfubin]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.hfbin.ucpm.service.impl;


import cn.hfbin.ucpm.entity.*;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.enums.RelationTypeEnum;
import cn.hfbin.ucpm.mapper.IdentityDepartRefMapper;
import cn.hfbin.ucpm.mapper.IdentityEmployeeMapper;
import cn.hfbin.ucpm.mapper.IdentityPositionRefMapper;
import cn.hfbin.common.core.constant.GrantTypeConstant;
import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.ucpm.params.*;
import cn.hfbin.ucpm.service.*;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: huangfubin
 * @Description: 员工身份信息表
 * @Date: 2021-06-16
 */
@Service
public class IdentityEmployeeServiceImpl extends ServiceImpl<IdentityEmployeeMapper, IdentityEmployee> implements IdentityEmployeeService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartService departService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IdentityPositionRefMapper identityPositionRefMapper;

    @Autowired
    private IdentityDepartRefMapper identityDepartRefMapper;

    @Autowired
    private RelationRoleService relationRoleService;
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param employeeQueryParams
     * @return
     */
    @Override
    public Page<IdentityInfoVo> page(Integer pageNo, Integer pageSize, EmployeeQueryParams employeeQueryParams) {
        Page<IdentityInfoVo> page = new Page(pageNo, pageSize);
        Page<IdentityInfoVo> pageList = baseMapper.selectPageList(page, employeeQueryParams);
        List<IdentityInfoVo> selectEmpList = pageList.getRecords();
        if(CollectionUtil.isNotEmpty(selectEmpList)){
            // 查询部门数据
            Set<Long> deptIds = selectEmpList.stream().map(IdentityInfoVo::getDeptId).collect(Collectors.toSet());
            DepartParams departParams = new DepartParams();
            departParams.setIds(deptIds);
            List<Depart> selectDeptList = departService.selectList(departParams);
            Map<Long, String> deptMaps = selectDeptList.stream().collect(Collectors.toMap(Depart::getId, Depart::getDeptName));

            // 查询岗位数据
            Set<Long> positionIds = selectEmpList.stream().map(IdentityInfoVo::getPositionId).collect(Collectors.toSet());
            PositionParams positionParams = new PositionParams();
            positionParams.setIds(positionIds);
            List<Position> positions = positionService.selectList(positionParams);
            Map<Long, String> positionMaps = positions
                    .stream().collect(Collectors.toMap(Position::getId, Position::getName));

            // 查询角色数据
            Set<String> roleLists = selectEmpList.stream().map(IdentityInfoVo::getRoleIds).collect(Collectors.toSet());
            Set<Long> roleIds = new HashSet<>();
            roleLists.forEach(data -> {
                String[] split = data.split(SpecialCharacterPool.COMMA);
                for (String s : split) {
                    roleIds.add(Long.valueOf(s));
                }
            });
            RoleParams roleParams = new RoleParams();
            roleParams.setIds(roleIds);
            List<Role> roles = roleService.selectList(roleParams);
            Map<Long, String> roleMaps = roles
                    .stream().collect(Collectors.toMap(Role::getId, Role::getRoleName));

            // 处理最终要显示的数据
            selectEmpList.forEach(data -> {
                data.setDeptName(deptMaps.get(data.getDeptId()));
                data.setPositionName(positionMaps.get(data.getPositionId()));

                String[] thisRoleIds = data.getRoleIds().split(SpecialCharacterPool.COMMA);
                StringBuilder roleNames = new StringBuilder();
                List<Long> roleListIds = new ArrayList<>();
                for (int i = 0; i < thisRoleIds.length; i++) {
                    roleNames.append(roleMaps.get(Long.valueOf(thisRoleIds[i])));
                    if(i < thisRoleIds.length - 1){
                        roleNames.append(SpecialCharacterPool.SLASH);
                    }
                    roleListIds.add(Long.valueOf(thisRoleIds[i]));
                }
                data.setRoleNames(String.valueOf(roleNames));
                data.setRoleListIds(roleListIds);
            });

        }
        return pageList;
    }
    /**
     * 根据id查询
     * @param
     * @return
     */
    @Override
    public IdentityEmployee getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        IdentityEmployee employee = baseMapper.selectById(id);
        Optional.ofNullable(employee).orElseThrow(() -> new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_IS_NULL));
        if(employee.getSystemDefault() == 1) {
            throw new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_ADMIN);
        }
        // 删除账号关联关系
        accountService.deleteById(employee.getAccountId());
        // 删除员工相关关联
        this.deleteEmpRef(employee.getId());
        return baseMapper.deleteById(id);
    }

    /**
     *
     * @description 根据accountId 或者mobile 查询用户信息
     * @param employee
     * @author huangfubin
     * @date 2021/7/21
     * @return BaseIdentityEmployee
     */
    @Override
    public IdentityEmployee select(IdentityEmployee employee) {
        LambdaQueryWrapper<IdentityEmployee> queryWrapper = new LambdaQueryWrapper<>();
        if(null != employee.getAccountId()){
            queryWrapper.eq(IdentityEmployee::getAccountId, employee.getAccountId());
        }
        if(StrUtil.isNotBlank(employee.getMobile())){
            queryWrapper.eq(IdentityEmployee::getMobile, employee.getMobile());
        }
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 更新数据(为null的字段会更新)
     * @param identityEmployee
     * @return
     */
    @Override
    public int updateSomeColumnById(IdentityEmployee identityEmployee) {
        return baseMapper.alwaysUpdateSomeColumnById(identityEmployee);
    }

    /**
     * 保存员工信息（如果不存在Account账号，则自动创建账号）
     * 1、校验部门、岗位、角色是否有效
     * 2、根据账号和手机号查询目前是否已经绑定过其它账号，若无则创建账号
     * 3、根据账号查询当前账号是否已经存在此身份，若存在则异常，否则创建
     * 4、绑定部门、岗位、角色关联关系
     * @param employeeParams
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveEmployee(EmployeeParams employeeParams) {
        // 1、校验：校验部门是否存在、校验角色是否存在、校验岗位是否存在
        this.checkPositionOrDeptOrRole(employeeParams);

        AccountParams accountParams = new AccountParams();
        accountParams.setUsername(employeeParams.getUsername());
        accountParams.setPassword(employeeParams.getPassword());
        accountParams.setMobile(employeeParams.getMobile());
        // 开启自动创建账【如果不存在账号则直接创建账号】
        accountParams.setAutoCreate(true);
        // 2、账号、手机号作为组合唯一识别
        Account account = accountService.selectOrSaveAccount(accountParams);
        // 3、员工身份信息
        IdentityEmployee employee = new IdentityEmployee();
        employee.setAccountId(account.getId());
        IdentityEmployee identityEmployee = this.select(employee);
        if(Objects.nonNull(identityEmployee)){
            throw new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_TO_MANY);
        }
        BeanUtils.copyProperties(employeeParams, employee);
        if(baseMapper.insert(employee) < 1) throw new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_ADD_ERROR);
        // 4、绑定部门、岗位、角色关联关系
        this.insertPositionRoleDept(employeeParams, employee);
        return 1;
    }

    /**
     * [初始化租户使用] 新增员工身份
     * @param employee
     * @return
     */
    @Override
    public int saveIgnoreTr(IdentityEmployee employee) {
        if(baseMapper.insert(employee) < 1) throw new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_ADD_ERROR);
        return 1;
    }

    /**
     *
     * @description 绑定部门、岗位、角色关联关系
     * @param employeeParams
     * @param employee
     * @author huangfubin
     * @date 2021/7/23
     * @return void
     */
    private void insertPositionRoleDept(EmployeeParams employeeParams, IdentityEmployee employee) {
        // 保存角色
        List<RelationRole> roleRefList = new ArrayList<>();
        employeeParams.getRoleListIds().forEach(data -> {
            RelationRole relationRole = new RelationRole();
            relationRole.setRoleId(data);
            relationRole.setRelationId(employee.getId());
            relationRole.setRelationType(RelationTypeEnum.USER_ID.getCode());
            relationRole.setTenantCode(SpringContextUtils.getTenantCode());
            relationRole.setDelFlag(0);
            roleRefList.add(relationRole);
        });
        if(relationRoleService.insertBatchSomeColumn(roleRefList) < 1) throw new LibraException(UcPmExceptionCode.FAILED_TO_ADD_ASSOCIATION);

        // 保存部门
        IdentityDepartRef identityDepartRef = new IdentityDepartRef();
        identityDepartRef.setDeptId(employeeParams.getDeptId());
        identityDepartRef.setIdentityId(employee.getId());
        // 主部门类型
        identityDepartRef.setDeptType(1);
        if(identityDepartRefMapper.insert(identityDepartRef) < 1) throw new LibraException(UcPmExceptionCode.FAILED_TO_ADD_ASSOCIATION);

        //保存岗位,岗位可为空
        if(employeeParams.getPositionId() != null){
            IdentityPositionRef identityPositionRef = new IdentityPositionRef();
            identityPositionRef.setPositionId(employeeParams.getPositionId());
            identityPositionRef.setIdentityId(employee.getId());
            if(identityPositionRefMapper.insert(identityPositionRef) < 1) throw new LibraException(UcPmExceptionCode.FAILED_TO_ADD_ASSOCIATION);
        }
    }

    /**
     * 更新员工(账号相关说明：只支持手机号更新，不支持账号和手机同时修改)
     * @param employeeParams
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer updateEmployee(EmployeeParams employeeParams) {
        // 1、校验：校验部门是否存在、校验角色是否存在、校验岗位是否存在
        this.checkPositionOrDeptOrRole(employeeParams);

        // 2、根据id判断当前修改人员信息是否存在此身份
        IdentityEmployee employee = baseMapper.selectById(employeeParams.getId());
        Optional.ofNullable(employee).orElseThrow(() -> new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_TO_MANY));
        if(employee.getSystemDefault() == 1) {
            throw new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_ADMIN);
        }
        AccountParams accountParams = new AccountParams();
        accountParams.setId(employee.getAccountId());
        accountParams.setMobile(employeeParams.getMobile());
        // 3、如果手机号被修改则更新账号表手机号
        if(!employee.getMobile().equals(employeeParams.getMobile())){
            AccountIdentityQueryParams params = new AccountIdentityQueryParams();
            params.setMobile(employeeParams.getMobile());
            Account account = accountService.select(params);
            if(Objects.nonNull(account)){
                throw new LibraException(UcPmExceptionCode.ACCOUNT_MOBILE_TO_MANY);
            }
            accountService.updateAccountMobile(accountParams);
        }
        // 删除之前绑定关系（这里处理逻辑，不管是否有更新都是直接先删除再插入新的关联关系）
        this.deleteEmpRef(employee.getId());

        // 4、绑定部门、岗位、角色关联关系
        this.insertPositionRoleDept(employeeParams, employee);

        IdentityEmployee newBaseEmployee = new IdentityEmployee();
        BeanUtils.copyProperties(employeeParams, newBaseEmployee);
        return baseMapper.updateById(newBaseEmployee);
    }



    /**
     * 删除员工相关关联关系
     * @param id
     */
    private void deleteEmpRef(Long id) {
        RelationRoleParams relationRoleParams = new RelationRoleParams();
        relationRoleParams.setId(id);
        relationRoleParams.setRelationTypeEnum(RelationTypeEnum.USER_ID);
        relationRoleService.deleteByRelationId(relationRoleParams);

        LambdaQueryWrapper<IdentityPositionRef> positionRefLambdaQueryWrapper = new LambdaQueryWrapper<>();
        positionRefLambdaQueryWrapper.eq(IdentityPositionRef::getIdentityId, id);
        identityPositionRefMapper.delete(positionRefLambdaQueryWrapper);

        LambdaQueryWrapper<IdentityDepartRef> departRefLambdaQueryWrapper = new LambdaQueryWrapper<>();
        departRefLambdaQueryWrapper.eq(IdentityDepartRef::getIdentityId, id);
        identityDepartRefMapper.delete(departRefLambdaQueryWrapper);
    }


    /**
     *
     * @description 校验：校验部门是否存在、校验角色是否存在、校验岗位是否存在
     * @param employeeParams
     * @author huangfubin
     * @date 2021/7/22
     * @return void
     */
    private void checkPositionOrDeptOrRole(EmployeeParams employeeParams){
        // 校验部门是否存在
        Depart depart = departService.getById(employeeParams.getDeptId());
        Optional.ofNullable(depart).orElseThrow(() -> new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_TO_MANY));

        // 校验角色是否存在
        List<Role> roleList = roleService.getByIds(employeeParams.getRoleListIds());
        List<Long> collect = roleList.stream().map(Role::getId).collect(Collectors.toList());
        if(employeeParams.getRoleListIds().size() != collect.size()){
            throw new LibraException(UcPmExceptionCode.ROLE_NULL);
        }
        //校验岗位是否存在
        if(employeeParams.getPositionId() != null){
            Position position = positionService.getById(employeeParams.getPositionId());
            Optional.ofNullable(position).orElseThrow(() -> new LibraException(UcPmExceptionCode.POSITION_IS_NULL));
        }
    }

}
