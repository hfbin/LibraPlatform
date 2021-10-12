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

package cn.hfbin.gateway.filter;

import cn.hfbin.gateway.gary.GrayLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Author: huangfubin
 * @Description: GrayFilter 类
 * @Date: 2021/9/16
 */
public class GrayFilter implements GlobalFilter, Ordered {

    //负载均衡器工厂对象，可以加载指定服务列表
    private final LoadBalancerClientFactory clientFactory;
    //负载均衡属性对象
    private LoadBalancerProperties properties;

    /**
     * 创建负载均衡器工厂对象
     * @param clientFactory
     * @param properties
     */
    public GrayFilter(LoadBalancerClientFactory clientFactory, LoadBalancerProperties properties) {
        this.clientFactory = clientFactory;
        this.properties = properties;
    }

    /**
     * 拦截所有请求
     * @param exchange：用户请求响应的所有对象
     * @param chain：链路调用对象
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //判断当前scheme是否为灰度服务，我们定义灰度服务的scheme为grayLb
        //根据不同策略获取灰度服务实例
        Mono<Response<ServiceInstance>> responseInstance = this.choose(exchange);

        //灰度服务链路调用
        return responseInstance.then(chain.filter(exchange));
    }

    /**
     * 调用GrayLoadBalancer的choose()方法，获取不同服务实例
     * @return
     */
    public Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        // 获取转发的路由
        Route attribute = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        GrayLoadBalancer grayLoadBalancer =
                new GrayLoadBalancer(
                        //用工厂对象加载可用的服务列表
                        clientFactory.getLazyProvider(Objects.requireNonNull(attribute).getUri().getHost(), ServiceInstanceListSupplier.class),
                        attribute.getUri().getHost());
        //因为会有根据版本信息或者用户身份信息或者IP实现,我们可以把请求头信息封装到Request中
        HttpHeaders headers = exchange.getRequest().getHeaders();
        //调用GrayLoadBalancer的choose()获取实例
        return grayLoadBalancer.choose(new DefaultRequest<>(headers));
    }


    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}