package cn.hfbin.bgg.common.entity;

import java.io.Serializable;

/**
 * @Author: huangfubin
 * @Description: Rule 类
 * @Date: 2021/10/13
 */
public class Condition implements Serializable {
    private static final long serialVersionUID = 1L;

    private String expression;
    private String routeKey;

    public Condition(String expression, String routeKey) {
        this.expression = expression;
        this.routeKey = routeKey;
    }
    public Condition() {}


    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }
}
