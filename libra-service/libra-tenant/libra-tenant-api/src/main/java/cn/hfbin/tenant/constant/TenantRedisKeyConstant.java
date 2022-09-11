package cn.hfbin.tenant.constant;

import cn.hfbin.common.core.constant.SpecialCharacterPool;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/6/29
 * @description:
 */
public interface TenantRedisKeyConstant {

    String BASE = "TENANT" + SpecialCharacterPool.S_COLON;

    // TENANT_NENU
    String TENANT_MENU = BASE + "MENU" + SpecialCharacterPool.S_COLON;

}
