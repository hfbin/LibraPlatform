package cn.hfbin.bgg.common.entity;

import java.io.Serializable;

/**
 * @Author: huangfubin
 * @Description: Rule 类
 * @Date: 2021/10/13
 */
public class Strategy implements Serializable{
    private static final long serialVersionUID = 1L;
    private String routeKey;

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }
}
