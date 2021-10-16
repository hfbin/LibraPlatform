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
