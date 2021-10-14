package cn.hfbin.bgg.gateway.context;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Author: huangfubin
 * @Description: GatewayStrategyContext 类
 * @Date: 2021/10/14
 */
public class GatewayStrategyContext {
    private static final ThreadLocal<GatewayStrategyContext> THREAD_LOCAL = ThreadLocal.withInitial(GatewayStrategyContext::new);

    private ServerWebExchange exchange;

    public static GatewayStrategyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    public ServerWebExchange getExchange() {
        return exchange;
    }

    public void setExchange(ServerWebExchange exchange) {
        this.exchange = exchange;
    }

}
