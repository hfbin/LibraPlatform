/*
 *    Copyright [2021] [LibraPlatform of copyright huangfubin]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.hfbin.bgg.common.configuration;

import cn.hfbin.bgg.common.adapter.Adapter;
import cn.hfbin.bgg.common.adapter.PluginAdapter;
import cn.hfbin.bgg.common.cache.RuleCache;
import cn.hfbin.bgg.common.constant.CommonConstant;
import cn.hfbin.bgg.common.initializer.ConfigInitializer;
import cn.hfbin.bgg.common.loader.LocalConfigLoader;
import cn.hfbin.bgg.common.service.StrategyContextService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
                return CommonConstant.CONFIG_PATH;
            }
        };
    }

    @Bean
    @ConditionalOnProperty(value = CommonConstant.LIBRA_BGG_ENABLED, matchIfMissing = true)
    public ConfigInitializer configInitializer() {
        return new ConfigInitializer();
    }

    @Bean
    public StrategyContextService strategyContextService() {
        return new StrategyContextService();
    }
}
