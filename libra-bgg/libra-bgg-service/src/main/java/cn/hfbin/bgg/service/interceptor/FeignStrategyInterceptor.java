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

package cn.hfbin.bgg.service.interceptor;

import cn.hfbin.bgg.common.constant.BggConstant;
import cn.hfbin.bgg.common.context.StrategyContextHolder;
import cn.hfbin.bgg.service.context.ServiceStrategyContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @Author: huangfubin
 * @Description: FeignStrategyInterceptor 类
 * @Date: 2021/10/15
 */
public class FeignStrategyInterceptor implements RequestInterceptor {
    @Autowired
    StrategyContextHolder strategyContextHolder;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前服务灰度规则
        String routeVersion = strategyContextHolder.getRouteVersion();
        if(StringUtils.isNotBlank(routeVersion)){
            requestTemplate.header(BggConstant.BGG_ROUTE, routeVersion);
        }else {
            // 如果当前没有灰度规则，则将上游传递得请求规则继续传递下去
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(attributes)) {
                String tenantCode = attributes.getRequest().getHeader(BggConstant.BGG_ROUTE);
                if (StringUtils.isNotBlank(tenantCode)) {
                    requestTemplate.header(BggConstant.BGG_ROUTE, tenantCode);
                }
            }
        }
    }
}
