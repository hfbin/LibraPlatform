package cn.hfbin.auth.provider.token;

import cn.hfbin.auth.enums.AuthExceptionCode;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.core.constant.GrantTypeConstant;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.core.jwt.model.AuthUserInfo;
import cn.hfbin.common.core.utils.FeignResponseUtil;
import cn.hfbin.ucpm.enums.AccountStatusEnum;
import cn.hfbin.ucpm.params.LoginParams;
import cn.hfbin.ucpm.vo.AccountVo;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: 账号、验证码、密码登录
 * @Date: 2021/7/29
 */
@Slf4j
@Component(GrantTypeConstant.PASSWORD)
public class PwdCaptchaStrategy extends AbstractTokenGranter {

    @Override
    public AuthUserInfo grant(LoginParams loginParam) {
        publicCheck();
        // 校验账号身份（账号是否存在、密码是否正确、账号是否正常）
        AccountVo accountVo = checkAccount(loginParam);
        // 密码错误
        if(!SecureUtil.md5(loginParam.getPassword()).equals(accountVo.getPassword())){
            throw new LibraException(AuthExceptionCode.PASSWORD_ERROR);
        }
        // 查询身份信息
        IdentityInfoVo identityInfoVo = FeignResponseUtil.getThrow(accountServiceClient.selectIdentityInfo(accountVo.getAccountId()));
        // 身份不存在，直接返回
        Optional.ofNullable(identityInfoVo).orElseThrow(()-> new LibraException(AuthExceptionCode.IDENTITY_INEXISTENCE));
        // 身份被禁用
        if(identityInfoVo.getStatus().equals(AccountStatusEnum.DISABLE.getCode())){
            throw new LibraException(AuthExceptionCode.IDENTITY_DISABLE);
        }
        // 创建 token
        return createToken(accountVo, identityInfoVo);
    }

}
