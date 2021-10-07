package cn.hfbin.ucpm.enums;

import lombok.Getter;

/**
 * @Author: huangfubin
 * @Description: 关联类型(1-用户ID关联,2-用户组织关联,3-岗位关联,4-部门关联 )
 * @Date: 2021/8/3
 */
public enum  RelationTypeEnum {
    USER_ID(1,"用户ID关联"),
    USER_GROUP(2,"用户组织关联"),
    POSITION_GROUP(3,"岗位关联"),
    DEPT_GROUP(4,"部门关联"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String msg;

    RelationTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
