package cn.hfbin.ucpm.enums;

import lombok.Getter;

/**
 * @Author: huangfubin
 * @Description: 据权限(10-全部;20-自定义;30-本级以及子级;40-本级;50-个人), 默认为10数
 * @Date: 2021/8/3
 */
public enum DsTypeEnum {
    ALL(10,"全部"),
    SUN_MENU(20,"自定义"),
    THIS_DEPT_ALL(30,"本级以及子级"),
    THIS_DEPT(40,"本级"),
    BTN(50,"个人"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String msg;

    DsTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
