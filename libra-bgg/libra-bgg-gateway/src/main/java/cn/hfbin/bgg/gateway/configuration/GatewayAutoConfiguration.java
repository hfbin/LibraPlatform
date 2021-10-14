package cn.hfbin.bgg.gateway.configuration;

import cn.hfbin.bgg.gateway.context.GatewayStrategyContextHolder;
import cn.hfbin.bgg.gateway.filter.AbstractGatewayStrategyRouteFilter;
import cn.hfbin.bgg.gateway.filter.DefaultGatewayStrategyRouteFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huangfubin
 * @Description: GatewayStrategyAutoConfiguration 类
 * @Date: 2021/10/14
 */
@Configuration
public class GatewayAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AbstractGatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new DefaultGatewayStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyContextHolder gatewayStrategyContextHolder() {
        return new GatewayStrategyContextHolder();
    }

}
