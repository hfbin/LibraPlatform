package cn.hfbin.bgg.common.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: huangfubin
 * @Description: Rule 类
 * @Date: 2021/10/13
 */
public class Rule implements Serializable {
    private static final long serialVersionUID = 1L;

    private Strategy strategy;

    private StrategyRelease strategyRelease;

    private Map<String, String> routes = new LinkedHashMap<>();

    public Map<String, String> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, String> routes) {
        this.routes = routes;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public StrategyRelease getStrategyRelease() {
        return strategyRelease;
    }

    public void setStrategyRelease(StrategyRelease strategyRelease) {
        this.strategyRelease = strategyRelease;
    }

}
