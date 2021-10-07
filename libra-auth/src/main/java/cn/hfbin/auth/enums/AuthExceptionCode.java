package cn.hfbin.auth.enums;

import cn.hfbin.common.core.api.ServiceCode;

/**
 * @Author: huangfubin
 * @Description: BaseExceptionCode 类
 * @Date: 2021/7/7
 */
public enum AuthExceptionCode implements ServiceCode {

    GRANTER_INEXISTENCE(1000, "grantType类型不存在"),
    SELECT_ERROR(1002, "查询失败"),

    ACCOUNT_IDENTITY_IS_NULL(1100, "账号不存在"),
    ACCOUNT_DISABLE(1101, "账号被禁用"),
    IDENTITY_DISABLE(1102, "身份被禁用"),
    IDENTITY_INEXISTENCE(1103, "身份类型不存在"),
    PASSWORD_ERROR(1104, "密码错误"),

    CLIENT_CODE_ERROR(1204, "非法客户端请求"),
    TENANT_CODE_ERROR(1205, "租户不存在"),
    TENANT_EXPIRED(1206, "租户已过期，请联系管理员！"),


    ;


    ;
    private int code;
    private String msg;

    AuthExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
