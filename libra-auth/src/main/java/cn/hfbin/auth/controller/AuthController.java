package cn.hfbin.auth.controller;

import cn.hfbin.auth.enums.AuthExceptionCode;
import cn.hfbin.auth.enums.GrantTypeEnum;
import cn.hfbin.auth.provider.CompositeTokenGranterContext;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.auth.constant.AuthRedisKeyConstant;
import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.token.model.AuthUserInfo;
import cn.hfbin.common.log.annotation.Log;
import cn.hfbin.common.log.enums.LogTypeEnum;
import cn.hfbin.common.log.enums.OptBehaviorEnum;
import cn.hfbin.common.redis.util.RedisUtil;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.params.LoginParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: huangfubin
 * @Description: AuthController 类
 * @Date: 2021/7/29
 */
@RestController
@Api(tags = "登录模块")
public class AuthController {

    @Autowired
    private CompositeTokenGranterContext compositeTokenGranterContext;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/token")
    @ApiOperation(value = "登录接口", notes = "登录接口支持用户名密码和手机号登录")
    @Log(desc = "认证服务-登录接口", logType = LogTypeEnum.LOGIN_LOG, optBehavior = OptBehaviorEnum.LOGIN)
    public ResponseData<AuthUserInfo> login(@RequestBody LoginParams loginParams){
        GrantTypeEnum grantType = GrantTypeEnum.getEnumByCode(loginParams.getGrantType());
        if(Objects.isNull(grantType)){
            throw new LibraException(AuthExceptionCode.GRANTER_INEXISTENCE);
        }
        return ResponseData.ok(compositeTokenGranterContext.getGranter(grantType).grant(loginParams));
    }


    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @Log(desc = "认证服务-退出接口", logType = LogTypeEnum.LOGIN_LOG, optBehavior = OptBehaviorEnum.LOGOUT)
    public ResponseData<String> logout(){
        //  key + client + identityId
        redisUtil.del(AuthRedisKeyConstant.USER_INFO_KEY + SpringContextUtils.getClientCode() + SpecialCharacterPool.S_COLON + SpringContextUtils.getIdentityId(),
                AuthRedisKeyConstant.USER_INTERFACE_KEY + SpringContextUtils.getClientCode() + SpecialCharacterPool.S_COLON + SpringContextUtils.getIdentityId());
        return ResponseData.ok(String.valueOf(System.currentTimeMillis()));
    }

}
