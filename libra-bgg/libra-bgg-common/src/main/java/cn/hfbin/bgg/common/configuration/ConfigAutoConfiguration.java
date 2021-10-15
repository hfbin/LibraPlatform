package cn.hfbin.bgg.common.configuration;

import cn.hfbin.bgg.common.adapter.Adapter;
import cn.hfbin.bgg.common.adapter.PluginAdapter;
import cn.hfbin.bgg.common.cache.RuleCache;
import cn.hfbin.bgg.common.constant.BggConstant;
import cn.hfbin.bgg.common.initializer.ConfigInitializer;
import cn.hfbin.bgg.common.loader.LocalConfigLoader;
import cn.hfbin.bgg.common.service.StrategyContextService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huangfubin
 * @Description: ConfigAutoConfiguration 类
 * @Date: 2021/10/14
 */
@Configuration
public class ConfigAutoConfiguration {

    @Bean
    public RuleCache ruleCache() {
        return new RuleCache();
    }

    @Bean
    public PluginAdapter pluginAdapter() {
        return new Adapter();
    }

    @Bean
    public LocalConfigLoader localConfigLoader() {
        return new LocalConfigLoader() {
            @Override
            protected String getPath() {
                return BggConstant.CONFIG_PATH;
            }
        };
    }

    @Bean
    public ConfigInitializer configInitializer() {
        return new ConfigInitializer();
    }

    @Bean
    public StrategyContextService strategyContextService() {
        return new StrategyContextService();
    }
}
