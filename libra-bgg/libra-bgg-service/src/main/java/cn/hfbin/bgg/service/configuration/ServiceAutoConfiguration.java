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

package cn.hfbin.bgg.service.configuration;

import cn.hfbin.bgg.common.configuration.ConfigAutoConfiguration;
import cn.hfbin.bgg.common.configuration.LoadBalancerConfiguration;
import cn.hfbin.bgg.common.constant.BggConstant;
import cn.hfbin.bgg.service.context.ServiceStrategyContextHolder;
import cn.hfbin.bgg.service.interceptor.FeignStrategyInterceptor;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huangfubin
 * @Description: ServiceAutoConfiguration 类
 * @Date: 2021/10/14
 */
@Configuration
@ConditionalOnProperty(value = BggConstant.LIBRA_BGG_ENABLED, matchIfMissing = true)
@AutoConfigureBefore({LoadBalancerConfiguration.class, ConfigAutoConfiguration.class})
public class ServiceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ServiceStrategyContextHolder gatewayStrategyContextHolder() {
        return new ServiceStrategyContextHolder();
    }


    @Bean
    @ConditionalOnMissingBean
    public RequestInterceptor feignStrategyInterceptor(){
        return new FeignStrategyInterceptor();
    }
}
