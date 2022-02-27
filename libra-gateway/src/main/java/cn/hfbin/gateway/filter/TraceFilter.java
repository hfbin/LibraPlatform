package cn.hfbin.gateway.filter;

import cn.hfbin.common.core.context.HeaderCode;
import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/2/26 9:41 下午
 * @description:
 */
@Component
public class TraceFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uuid = IdUtil.simpleUUID();
        exchange.getRequest().mutate().header(HeaderCode.TRACE_ID, uuid);
        MDC.put(HeaderCode.TRACE_ID, uuid);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -999;
    }
}
