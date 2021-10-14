package cn.hfbin.bgg.service.configuration;

import cn.hfbin.bgg.service.context.ServiceStrategyContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huangfubin
 * @Description: ServiceAutoConfiguration 类
 * @Date: 2021/10/14
 */
@Configuration
public class ServiceAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public ServiceStrategyContextHolder gatewayStrategyContextHolder() {
        return new ServiceStrategyContextHolder();
    }
}
