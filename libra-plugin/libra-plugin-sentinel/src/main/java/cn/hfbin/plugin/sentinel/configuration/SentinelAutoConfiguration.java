package cn.hfbin.plugin.sentinel.configuration;

import cn.hfbin.plugin.nacos.processor.NacosListenerProcessor;
import cn.hfbin.plugin.sentinel.processor.SentinelRuleNacosProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/4/18 12:29 上午
 * @description:
 */
@Configuration
public class SentinelAutoConfiguration {

    @ConditionalOnClass(NacosListenerProcessor.class)
    protected static class SentinelSRuleNacosConfiguration {
        @Bean
        public NacosListenerProcessor sentinelStrategyFlowRuleNacosProcessor() {
            return new SentinelRuleNacosProcessor();
        }
    }
}
