package cn.hfbin.auth.provider;

import cn.hfbin.common.token.model.AuthUserInfo;
import cn.hfbin.ucpm.params.LoginParams;

/**
 * @Author: huangfubin
 * @Description: TokenGranter 类
 * @Date: 2021/7/29
 */
public interface TokenGranterStrategy {

    AuthUserInfo grant(LoginParams loginParam);
}
