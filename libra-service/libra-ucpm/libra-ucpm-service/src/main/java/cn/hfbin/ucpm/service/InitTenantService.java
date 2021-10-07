package cn.hfbin.ucpm.service;

import cn.hfbin.ucpm.params.InitTenantParams;
import cn.hfbin.ucpm.vo.InitTenantVo;

/**
 * @Author: huangfubin
 * @Description: 创建租户需要在此服务初始化的信息
 * @Date: 2021/8/9
 */
public interface InitTenantService {

    /**
     * 初始化租户相关账号部门角色信息
     * @param params
     * @return
     */
    InitTenantVo initTenant(InitTenantParams params);

    /**
     *  同步租户默认管理员角色权限
     * @param params
     * @return
     */
    int syncAdminRoleMenuPermission(InitTenantParams params);
}
