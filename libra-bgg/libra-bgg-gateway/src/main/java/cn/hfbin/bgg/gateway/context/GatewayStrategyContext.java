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

package cn.hfbin.bgg.gateway.context;

import org.springframework.web.server.ServerWebExchange;

/**
 * @Author: huangfubin
 * @Description: GatewayStrategyContext 类
 * @Date: 2021/10/14
 */
public class GatewayStrategyContext {
    private static final ThreadLocal<GatewayStrategyContext> THREAD_LOCAL = ThreadLocal.withInitial(GatewayStrategyContext::new);

    private ServerWebExchange exchange;

    public static GatewayStrategyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    public ServerWebExchange getExchange() {
        return exchange;
    }

    public void setExchange(ServerWebExchange exchange) {
        this.exchange = exchange;
    }

}
