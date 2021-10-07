package cn.hfbin.ucpm.enums;

import cn.hfbin.common.core.api.ServiceCode;

/**
 * @Author: huangfubin
 * @Description: BaseExceptionCode 类
 * @Date: 2021/7/7
 */
public enum UcPmExceptionCode implements ServiceCode {


    // 公共编码
    SYSTEM_DEFAULT_DELETE(1000,"系统默认数据无法删除"),
    SYSTEM_DEFAULT_UPDATE(1000,"系统默认数据无法更新"),

    SELECT_IS_NULL(1100,"不存在数据"),
    SELECT_PARENT_ID_ERROR(1110,"父id不存在"),
    FAILED_TO_ADD_ASSOCIATION(1120,"新增关联失败"),

    // 账号相关编码
    ACCOUNT_TO_MANY(2000,"账号或手机号已跟其它账号绑定"),
    ACCOUNT_MOBILE_TO_MANY(2001,"手机号已跟其它账号绑定"),
    ACCOUNT_IS_NULL(2010,"账号不存在"),

    // 身份相关编码
    IDENTITY_EMPLOYEE_TO_MANY(2100,"此员工已存在"),
    IDENTITY_EMPLOYEE_IS_NULL(2101,"员工不存在"),
    IDENTITY_EMPLOYEE_ADD_ERROR(2102,"新增员工失败"),
    IDENTITY_EMPLOYEE_ADMIN(2103,"管理员账号不允许修改"),
    IDENTITY_INEXISTENCE(2104, "身份类型不存在"),


    // 部门相关编码
    DEPT_IS_NULL(2200,"部门不存在"),
    DEPT_EMP_EXIST(2210,"此部门下有关联员工无法删除,请移除关联再删除"),

    // 岗位相关编码
    POSITION_IS_NULL(2300,"岗位不存在"),
    POSITION_EMP_EXIST(2310,"此岗位下有关联员工无法删除,请移除关联再删除"),

    // 角色相关编码
    ROLE_NULL(2400,"角色不存在"),
    ROLE_ADMIN(2401,"默认角色不允许删除"),
    ROLE_BIND(2402,"角色已关联用户无法，请先解绑用户角色后删除"),

    // MENU
    MENU_NULL(2450,"角色不存在"),

    // 客户端相关编码
    CLIENT_CODE_IS_NULL(2500,"租户编码不能为空"),
    CLIENT_CODE_CANNOT_BE_MODIFIED(2501,"客户端编码不可修改"),
    CLIENT_CANNOT_BE_DELETE(2502,"客户端已关联菜单，无法删除"),

    ;
    private int code;
    private String msg;

    UcPmExceptionCode(int code, String msg) {
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
