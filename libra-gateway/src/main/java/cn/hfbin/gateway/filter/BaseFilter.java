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

import cn.hfbin.common.core.context.HeaderCode;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.core.jwt.model.JwtUserInfo;
import cn.hfbin.common.core.jwt.AuthUtil;
import cn.hfbin.gateway.enums.GatewayExceptionCode;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: BaseFilter 类
 * @Date: 2021/6/28
 */
@Slf4j
@Component
public class BaseFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthUtil authUtil;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 租户
        String clientCode = exchange.getRequest().getHeaders().getFirst(HeaderCode.CLIENT_CODE);
        // 租户
        String tenantCode = exchange.getRequest().getHeaders().getFirst(HeaderCode.TENANT_CODE);
        // token
        String token = exchange.getRequest().getHeaders().getFirst(HeaderCode.TOKEN);
        // identityType
        String identityType = exchange.getRequest().getHeaders().getFirst(HeaderCode.IDENTITY_TYPE);

        log.info("请求路径:{}", exchange.getRequest().getPath());
        log.info("请求租户:{}", tenantCode);
        log.info("请求客户端:{}", clientCode);
        log.info("请求token:{}", token);
        log.info("请求身份类型:{}", identityType);


        //设置请求头
        Map<String, String> headers = new HashMap<>();

        if (StrUtil.isNotBlank(token)) {
            JwtUserInfo authInfo = authUtil.getAuthInfo(token);
            Optional.ofNullable(authInfo).orElseThrow(() -> new LibraException(GatewayExceptionCode.TOKEN_INVALID));
            headers.put(HeaderCode.ACCOUNT_ID, String.valueOf(authInfo.getAccountId()));
            headers.put(HeaderCode.IDENTITY_ID, String.valueOf(authInfo.getIdentityId()));
            headers.put(HeaderCode.DEPT_ID, String.valueOf(authInfo.getDeptId()));
            headers.put(HeaderCode.DEPT_CODE, authInfo.getDeptCode());
            headers.put(HeaderCode.USERNAME, authInfo.getUsername());
            headers.put(HeaderCode.DATA_SCOPE, String.valueOf(authInfo.getDeptCode()));
        }
        headers.put(HeaderCode.IDENTITY_TYPE, identityType);
        headers.put(HeaderCode.TENANT_CODE, tenantCode);
        headers.put(HeaderCode.CLIENT_CODE, clientCode);
        headers.put(HeaderCode.IP, null);
//        headers.put(HeaderCode.TOKEN, token);
        setHeader(exchange, headers);
        return chain.filter(exchange);
    }

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

    @Override
    public int getOrder() {
        return -99;
    }
}
