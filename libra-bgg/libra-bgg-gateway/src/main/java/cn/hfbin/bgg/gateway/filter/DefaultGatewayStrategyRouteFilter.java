package cn.hfbin.bgg.gateway.filter;

import cn.hfbin.bgg.common.context.StrategyContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: huangfubin
 * @Description: DefaultGatewayStrategyRouteFilter 类
 * @Date: 2021/10/14
 */
public class DefaultGatewayStrategyRouteFilter extends AbstractGatewayStrategyRouteFilter{

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Override
    public String getRouteVersion() {
        return strategyContextHolder.getRouteVersion();
    }

}
