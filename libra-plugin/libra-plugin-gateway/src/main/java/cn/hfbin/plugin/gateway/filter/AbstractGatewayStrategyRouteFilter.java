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

package cn.hfbin.plugin.gateway.filter;

import cn.hfbin.plugin.bgg.context.StrategyContextHolder;
import cn.hfbin.plugin.common.constant.CommonConstant;
import cn.hfbin.plugin.gateway.context.GatewayStrategyContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: huangfubin
 * @Description: AbstractGatewayStrategyRouteFilter 类
 * @Date: 2021/10/14
 */
public abstract class AbstractGatewayStrategyRouteFilter implements GatewayStrategyFilter {
    @Autowired
    private StrategyContextHolder strategyContextHolder;

    /**
     * 将灰度具体需要走的链路放到请求头中
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayStrategyContext.getCurrentContext().setExchange(exchange);

        String routeVersion = strategyContextHolder.getRouteVersion();
        if(StringUtils.isNotBlank(routeVersion)){
            exchange.getRequest().mutate().header(CommonConstant.BGG_ROUTE, routeVersion);
        }

        GatewayStrategyContext.clearCurrentContext();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return GatewayOrderedFilter.SET_ROUTE_VERSION_ORDERED_FILTER;
    }

}
