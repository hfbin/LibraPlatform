package cn.hfbin.base.enums;

import cn.hfbin.common.core.api.ServiceCode;

/**
 * @Author: huangfubin
 * @Description: BaseExceptionCode 类
 * @Date: 2021/7/7
 */
public enum BaseExceptionCode implements ServiceCode {


    // 公共编码
    SYSTEM_DEFAULT_DELETE(1000,"系统默认数据无法删除"),
    SYSTEM_DEFAULT_UPDATE(1000,"系统默认数据无法更新"),

    ;
    private int code;
    private String msg;

    BaseExceptionCode(int code, String msg) {
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
