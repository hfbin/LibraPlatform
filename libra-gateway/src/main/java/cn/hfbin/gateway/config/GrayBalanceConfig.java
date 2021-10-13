///*
// *    Copyright [2021] [LibraPlatform of copyright huangfubin]
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package cn.hfbin.gateway.config;
//
//import cn.hfbin.gateway.gary.GrayLoadBalancer;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
//import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
///**
// * @Author: huangfubin
// * @Description: GrayBalanceConfig 类
// * @Date: 2021/9/16
// */
//@Configuration
//public class GrayBalanceConfig {
//
////    /**
////     * 过滤器配置
////     */
////    @Bean
////    @ConditionalOnMissingBean({GrayFilter.class})
////    public GrayFilter grayFilter(LoadBalancerClientFactory clientFactory, LoadBalancerProperties properties) {
////        return new GrayFilter(clientFactory, properties);
////    }
//
//
//}
