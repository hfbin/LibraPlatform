package cn.hfbin.bgg.gateway.filter;

import cn.hfbin.bgg.gateway.context.GatewayStrategyContext;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: huangfubin
 * @Description: AbstractGatewayStrategyRouteFilter 类
 * @Date: 2021/10/14
 */
public abstract class AbstractGatewayStrategyRouteFilter implements GatewayStrategyFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayStrategyContext.getCurrentContext().setExchange(exchange);



        GatewayStrategyContext.clearCurrentContext();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 100;
    }

}
