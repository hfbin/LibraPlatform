package cn.hfbin.plugin.common.enums;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/5/3 11:52 下午
 * @description: 服务类型：1、网关 2、非网关
 */
public enum ServiceTypeEnum {
    GATEWAY(1,"网关"),
    SERVICE(2,"非网关"),
    ;

    private final int code;
    private final String msg;

    ServiceTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
