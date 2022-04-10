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

import cn.hfbin.common.core.constant.ConfigValueConstant;
import cn.hfbin.common.core.exception.CommonExceptionCode;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.gateway.constant.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



/**
 * @Author: huangfubin
 * @Description: BaseFilter 类
 * @Date: 2021/6/28
 */
@Slf4j
@Component
public class UriFilter extends BaseFilter {

    @Value("${" + ConfigValueConstant.SPRING_PROFILES_ACTIVE + "}")
    private String env;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 演示环境部分接口不允许操作
        if(env.equals("prod")){
            if(super.checkPath(super.getPath(exchange), gatewayProperties.getNoOptPath())){
                throw new LibraException(CommonExceptionCode.OPT_ERROR);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return OrderConstant.URI_ORDER;
    }
}
