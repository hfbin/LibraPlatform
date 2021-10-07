package cn.hfbin.auth.controller;

import cn.hfbin.auth.enums.AuthExceptionCode;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.core.constant.AuthRedisKeyConstant;
import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.redis.util.RedisUtil;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: MenuResourceController 类
 * @Date: 2021/8/9
 */
@RestController
@Api(tags = "账号身份信息模块")
public class AccountIdentityController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/user-info")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    public ResponseData<IdentityInfoVo> info(){
        IdentityInfoVo identityInfoVo = (IdentityInfoVo)redisUtil.strGet(AuthRedisKeyConstant.USER_INFO_KEY + SpringContextUtils.getClientCode() + SpecialCharacterPool.DOUBLE_COLON + SpringContextUtils.getIdentityId());
        Optional.ofNullable(identityInfoVo).orElseThrow(() -> new LibraException(AuthExceptionCode.SELECT_ERROR));
        return ResponseData.ok(identityInfoVo);
    }

}
