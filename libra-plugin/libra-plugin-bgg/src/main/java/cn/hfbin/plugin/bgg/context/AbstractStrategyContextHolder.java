package cn.hfbin.plugin.bgg.context;

import cn.hfbin.plugin.bgg.service.StrategyContextService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: huangfubin
 * @Description: AbstractStrategyContextHolder 类
 * @Date: 2021/10/14
 */
public abstract class AbstractStrategyContextHolder implements StrategyContextHolder {

    @Autowired
    private StrategyContextService strategyContextService;

    @Override
    public String getRouteVersion() {
        return strategyContextService.getRouteVersion();
    }
}
