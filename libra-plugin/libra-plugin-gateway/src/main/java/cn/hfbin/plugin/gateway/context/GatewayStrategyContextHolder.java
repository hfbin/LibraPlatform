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

package cn.hfbin.plugin.gateway.context;

import cn.hfbin.plugin.bgg.context.AbstractStrategyContextHolder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Author: huangfubin
 * @Description: GatewayStrategyContextHolder 类
 * @Date: 2021/10/14
 */
public class GatewayStrategyContextHolder extends AbstractStrategyContextHolder {

    @Override
    public String getHeader(String name) {
        ServerHttpRequest request = getServerHttpRequest();
        if (request == null) {
            return null;
        }
        return request.getHeaders().getFirst(name);
    }


    public ServerHttpRequest getServerHttpRequest() {
        ServerWebExchange exchange = getExchange();
        if (exchange == null) {
            return null;
        }
        return exchange.getRequest();
    }

    public ServerWebExchange getExchange() {
        return GatewayStrategyContext.getCurrentContext().getExchange();
    }

}
