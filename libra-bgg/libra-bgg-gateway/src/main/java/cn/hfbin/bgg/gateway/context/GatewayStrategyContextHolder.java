package cn.hfbin.bgg.gateway.context;

import cn.hfbin.bgg.common.context.AbstractStrategyContextHolder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Author: huangfubin
 * @Description: GatewayStrategyContextHolder 类
 * @Date: 2021/10/14
 */
public class GatewayStrategyContextHolder extends AbstractStrategyContextHolder {

    @Override
    public String getHeader(String name) {
        ServerHttpRequest request = getServerHttpRequest();
        if (request == null) {
            return null;
        }
        return request.getHeaders().getFirst(name);
    }


    public ServerHttpRequest getServerHttpRequest() {
        ServerWebExchange exchange = getExchange();
        if (exchange == null) {
            return null;
        }
        return exchange.getRequest();
    }

    public ServerWebExchange getExchange() {
        return GatewayStrategyContext.getCurrentContext().getExchange();
    }

}
