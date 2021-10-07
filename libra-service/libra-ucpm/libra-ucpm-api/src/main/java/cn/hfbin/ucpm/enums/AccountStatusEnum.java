package cn.hfbin.ucpm.enums;

import lombok.Getter;

/**
 * @Author: huangfubin
 * @Description: AccountStatusEnum 类
 * @Date: 2021/7/21
 */
public enum AccountStatusEnum {

    DISABLE(0,"禁用"),
    ENABLE(1,"启用"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String msg;

    AccountStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
