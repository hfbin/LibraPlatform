package cn.hfbin.ucpm.enums;

import lombok.Getter;

/**
 * @Author: huangfubin
 * @Description: 菜单类型(0-主菜单,1-子菜单,2-按钮权限)
 * @Date: 2021/8/3
 */
public enum MenuTypeEnum {
    MASTER_MENU(0,"主菜单"),
    SUN_MENU(1,"子菜单"),
    BTN(2,"按钮权限"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String msg;

    MenuTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
