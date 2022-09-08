package cn.hfbin.auth.provider.token;

import cn.hfbin.auth.constant.GrantTypeConstant;
import cn.hfbin.common.token.model.AuthUserInfo;
import cn.hfbin.ucpm.params.LoginParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: huangfubin
 * @Description: 手机号验证码登录
 * @Date: 2021/7/29
 */
@Slf4j
@Component(GrantTypeConstant.MOBILE)
public class MobileCaptchaStrategy extends AbstractTokenGranter {
    @Override
    public AuthUserInfo grant(LoginParams loginParam) {
        log.info("MobileCaptchaTokenGranter");
        return null;
    }
}
