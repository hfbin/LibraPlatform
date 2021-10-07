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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author: huangfubin
 * @Description: BaseFilter 类
 * @Date: 2021/6/28
 */
@Slf4j
@Component
public class UriFilter implements GlobalFilter, Ordered {

    private final static List<String> uriList = ListUtil.toList(
          "/api/ucpm/depart/delete",
            "/api/ucpm/role/delete",
            "/api/ucpm/role/group/delete",
            "/api/ucpm/position/delete",
            "/api/ucpm/role/saveRoleMenu",
            "/api/tr/menu/template/delete",
            "/api/tr/menu/template/edit",
            "/api/tr/menu/template/save",
            "/api/tr/menu/template/syncTenantMenuPermission",
            "/api/tr/tenant/delete",
            "/api/tr/tenant/edit",
            "/api/ucpm/client/delete",
            "/api/ucpm/client/update",
            "/api/ucpm/interface/edit",
            "/api/ucpm/interface/delete",
            "/api/ucpm/interface/save",
            "/api/ucpm/menu/interfaceRef/save",
            "/api/ucpm/menu/edit",
            "/api/ucpm/menu/save",
            "/api/ucpm/menu/delete",
            "/api/ucpm/version/delete",
            "/api/ucpm/version/save",
            "/api/ucpm/version/edit",
            "/api/ucpm/ds/edit",
            "/api/ucpm/version/edit"
    );

    @Value("${" + ConfigValueConstant.SPRING_PROFILES_ACTIVE + "}")
    private String env;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = String.valueOf(exchange.getRequest().getPath());
        // 演示环境部分接口不允许操作
        if(env.equals("prod")){
            uriList.forEach(data -> {
                if(path.contains(data)){
                    throw new LibraException(CommonExceptionCode.OPT_ERROR);
                }
            });
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
