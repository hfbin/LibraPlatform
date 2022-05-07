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


import cn.hfbin.common.core.jwt.AuthUtil;
import cn.hfbin.gateway.properties.LibraGatewayProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import java.util.Map;
import java.util.Set;

/**
 * @Author: huangfubin
 * @Description: BaseFilter 类
 * @Date: 2021/6/28
 */
@Slf4j
@Component
public abstract class BaseFilter implements GlobalFilter, Ordered {

    @Autowired
    protected AuthUtil authUtil;

    @Autowired
    protected LibraGatewayProperties gatewayProperties;

    protected final PathMatcher pathMatcher = new AntPathMatcher();

    protected void setHeader(ServerWebExchange exchange, Map<String, String> headers) {
        exchange.getRequest().mutate().headers(httpHeaders -> {
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                if (value != null) {
                    httpHeaders.add(key, value);
                }
            }
        });
    }

    protected boolean checkPath(String requestPath, Set<String> configPathList) {
        if(CollectionUtils.isEmpty(configPathList)){
            return false;
        }
        for (String configPath : configPathList) {
            if (pathMatcher.match(configPath, requestPath)) {
                return true;
            }
        }
        return false;
    }

    protected String getPath(ServerWebExchange exchange) {
        return String.valueOf(exchange.getRequest().getPath());
    }

}
