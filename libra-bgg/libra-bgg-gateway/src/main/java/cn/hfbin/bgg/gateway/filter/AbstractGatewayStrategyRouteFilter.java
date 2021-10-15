package cn.hfbin.bgg.gateway.filter;

import cn.hfbin.bgg.common.adapter.PluginAdapter;
import cn.hfbin.bgg.common.constant.BggConstant;
import cn.hfbin.bgg.common.context.StrategyContextHolder;
import cn.hfbin.bgg.gateway.context.GatewayStrategyContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: huangfubin
 * @Description: AbstractGatewayStrategyRouteFilter 类
 * @Date: 2021/10/14
 */
public abstract class AbstractGatewayStrategyRouteFilter implements GatewayStrategyFilter {
    @Autowired
    StrategyContextHolder strategyContextHolder;

    /**
     * 将灰度具体需要走的链路放到请求头中
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayStrategyContext.getCurrentContext().setExchange(exchange);

        String routeVersion = strategyContextHolder.getRouteVersion();
        if(StringUtils.isNotBlank(routeVersion)){
            exchange.getRequest().mutate().header(BggConstant.BGG_ROUTE, routeVersion);
        }

        GatewayStrategyContext.clearCurrentContext();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 100;
    }

}
