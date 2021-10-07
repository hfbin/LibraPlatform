package cn.hfbin.tenant.enums;

import cn.hfbin.common.core.api.ServiceCode;

/**
 * @Author: huangfubin
 * @Description: BaseExceptionCode 类
 * @Date: 2021/7/7
 */
public enum TenantExceptionCode implements ServiceCode {

    MENU_TEMPLATE_NULL(7000,"菜单模板不存在"),
    MENU_TEMPLATE_BIND_TANANT(7001,"菜单模板已被租户关联，解绑后可删除"),


    TENANT_CODE_IS_NULL(7100,"租户编码不能为空"),
    TENANT_IS_NULL(7101,"租户不存在"),
    TENANT_CODE_ALREADY_EXISTS(7102,"租户编码不可修改"),

    ;
    private int code;
    private String msg;

    TenantExceptionCode(int code, String msg) {
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
