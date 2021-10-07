package cn.hfbin.ucpm.service.impl;

import cn.hfbin.common.core.enums.IdentityEnum;
import cn.hfbin.ucpm.entity.*;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.enums.MenuTypeEnum;
import cn.hfbin.ucpm.enums.RelationTypeEnum;
import cn.hfbin.ucpm.mapper.IdentityDepartRefMapper;
import cn.hfbin.ucpm.mapper.IdentityPositionRefMapper;
import cn.hfbin.ucpm.params.AccountIdentityQueryParams;
import cn.hfbin.ucpm.params.RelationRoleParams;
import cn.hfbin.ucpm.params.TreeParams;
import cn.hfbin.ucpm.service.*;
import cn.hfbin.common.core.constant.AuthRedisKeyConstant;
import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.exception.CommonExceptionCode;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.core.utils.FeignResponseUtil;
import cn.hfbin.common.redis.util.RedisUtil;
import cn.hfbin.tenant.client.TrTenantServiceClient;
import cn.hfbin.ucpm.strategy.identity.IdentityContext;
import cn.hfbin.ucpm.vo.*;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: huangfubin
 * @Description: AccountIdentityServiceImpl 类
 * @Date: 2021/8/9
 */
@Service
public class AccountIdentityServiceImpl implements AccountIdentityService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private IdentityPositionRefMapper identityPositionRefMapper;

    @Autowired
    private IdentityDepartRefMapper identityDepartRefMapper;

    @Autowired
    private RelationRoleService relationRoleService;

    @Autowired
    private TrTenantServiceClient trTenantServiceClient;

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IdentityContext identityContext;

    /**
     *
     * @description 查询用户账号身份信息
     * @param accountParams
     * @author huangfubin
     * @date 2021/8/8
     * @return AccountIdentityVo
     */
    @Override
    public AccountVo selectAccount(AccountIdentityQueryParams accountParams) {
        Account account = accountService.select(accountParams);
        if(Objects.isNull(account)){
            return null;
        }
        AccountVo accountVo = new AccountVo();
        accountVo.setAccountStatus(account.getStatus());
        accountVo.setAccountId(account.getId());
        accountVo.setMobile(account.getMobile());
        accountVo.setUsername(account.getUsername());
        accountVo.setPassword(account.getPassword());
        return accountVo;
    }

    /**
     * 根据id查询
     * @param accountId 账号id
     * @return
     */
    @Override
    public IdentityInfoVo selectById(Long accountId){
        IdentityEnum identityEnum = IdentityEnum.getEnumByCode(SpringContextUtils.getIdentityType());
        if(Objects.isNull(identityEnum)){
            throw new LibraException(UcPmExceptionCode.IDENTITY_INEXISTENCE);
        }
        IdentityInfoVo identityInfoVo = identityContext.getIdentityStrategy(identityEnum).selectIdentityInfo(accountId);
        return selectIdentityOtherInfo(identityInfoVo);
    }
    /**
     *
     * @description 查询部门、岗位、角色相关信息
     * @param identityInfoVo
     * @author huangfubin
     * @date 2021/9/7
     * @return cn.hfbin.ucpm.vo.IdentityInfoVo
     */
    private IdentityInfoVo selectIdentityOtherInfo(IdentityInfoVo identityInfoVo){
        Long identityId = identityInfoVo.getId();
        // 查询主部门
        List<Depart> departs = identityDepartRefMapper.selectListByEmpId(identityId, true);
        if(CollectionUtil.isNotEmpty(departs)){
            identityInfoVo.setDeptId(departs.get(0).getId());
            identityInfoVo.setDeptName(departs.get(0).getDeptName());
            identityInfoVo.setDeptCode(departs.get(0).getDeptCode());
        }
        // 查询岗位
        Position position = identityPositionRefMapper.selectListByEmpId(identityId);
        if(Objects.nonNull(position)){
            identityInfoVo.setPositionId(position.getId());
            identityInfoVo.setPositionName(position.getName());
        }
        // 查询角色
        RelationRoleParams relationRoleParams = new RelationRoleParams();
        relationRoleParams.setId(identityId);
        relationRoleParams.setRelationTypeEnum(RelationTypeEnum.USER_ID);
        List<RelationRoleVo> baseRoles = relationRoleService.selectList(relationRoleParams);
        if(CollectionUtil.isNotEmpty(baseRoles)){
            identityInfoVo.setRoleListIds(baseRoles.stream().map(RelationRoleVo::getId).collect(Collectors.toList()));
        }
        return identityInfoVo;
    }

    /**
     *
     * @description 查询用户菜单及按钮权限，如果有多身份的设计需另外扩展目前写死一个身份
     * @param
     * @author huangfubin
     * @date 2021/8/8
     * @return cn.hfbin.base.vo.AuthVo
     */
    @Override
    public MenuResourceVo selectUserMenu(TreeParams treeParams) {
        IdentityInfoVo identityInfoVo = this.selectById(SpringContextUtils.getAccountId());
        List<Long> ids = new ArrayList<>();
        // 员工id
        ids.add(identityInfoVo.getId());
        // 岗位id
        ids.add(identityInfoVo.getPositionId());
        // 部门id
        ids.add(identityInfoVo.getDeptId());
        // 角色id
        if(CollectionUtil.isNotEmpty(identityInfoVo.getRoleListIds())){
            ids.addAll(identityInfoVo.getRoleListIds());
        }
        RelationRoleParams relationRoleParams = new RelationRoleParams();
        relationRoleParams.setIds(ids);
        // 查询租户已经开通的菜单权限
        List<Long> menuIds = FeignResponseUtil.get(trTenantServiceClient.selectMenu(SpringContextUtils.getTenantCode()));
        if(CollectionUtil.isEmpty(menuIds)){
            throw new LibraException(CommonExceptionCode.TENANT_AUTH_NULL);
        }
        // 查询用户菜单权限
        List<Menu> menuLists = accountService.selectUserMenu(relationRoleParams);

        // 过滤不在租户内的菜单
        List<Menu> menuList = menuLists.stream().filter(o -> menuIds.contains(o.getId())).collect(Collectors.toList());
        // 查询接口权限code，接口鉴权使用
        List<String> interfaceCodes = interfaceService.selectInterfaceCode(menuList.stream().map(Menu::getId).collect(Collectors.toList()));
        if(CollectionUtil.isNotEmpty(interfaceCodes)){
            // 将接口权限code放到缓存，在鉴权时候会使用到
            //  key + client + identityId
            redisUtil.strSet(AuthRedisKeyConstant.USER_INTERFACE_KEY + SpringContextUtils.getClientCode() + SpecialCharacterPool.DOUBLE_COLON + identityInfoVo.getId(), interfaceCodes);
        }
        // 菜单
        List<Menu> routerMenuList = menuList.stream().filter(o -> (o.getMenuType() == MenuTypeEnum.MASTER_MENU.getCode() || o.getMenuType() == MenuTypeEnum.SUN_MENU.getCode())).collect(Collectors.toList());
        // 按钮
        List<String> btnList = menuList.stream().filter(o -> (o.getMenuType() == MenuTypeEnum.BTN.getCode())).map(Menu::getPerms).distinct().collect(Collectors.toList());
        MenuResourceVo menuResourceVo = new MenuResourceVo();
        // 菜单处理
        menuResourceVo.setRouterVos(CollectionUtil.isNotEmpty(routerMenuList) ? RouterVo.listToTree(routerMenuList) : null);
        menuResourceVo.setBtnAuths(btnList);
        return menuResourceVo;
    }

}
