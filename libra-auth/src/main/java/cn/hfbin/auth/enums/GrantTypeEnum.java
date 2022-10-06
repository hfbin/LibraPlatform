package cn.hfbin.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GrantTypeEnum {

    MOBILE("mobile", "mobileCaptchaStrategy", "手机验证码登录"),
    PASSWORD("password", "pwdCaptchaStrategy", "密码登录"),
    WX_OPEN_ID("wx_open_id", "wxOpenIdStrategy", "微信OPENID登录"),
            ;
    private final String code;
    private final String beanName;
    private final String msg;

    public static GrantTypeEnum getEnumByCode(String code){
        for (GrantTypeEnum grantTypeEnum:GrantTypeEnum.values()) {
            if(grantTypeEnum.getCode().equals(code)){
                return grantTypeEnum;
            }
        }
        return null;
    }
}
