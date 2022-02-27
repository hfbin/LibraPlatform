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

import cn.hfbin.bgg.common.constant.CommonConstant;
import cn.hfbin.bgg.common.context.StrategyContextHolder;
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
        String routeVersion = strategyContextHolder.getRouteVersion();
        if(StringUtils.isNotBlank(routeVersion)){
            requestTemplate.header(CommonConstant.BGG_ROUTE, routeVersion);
        }else {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(attributes)) {
                String bggRoute = attributes.getRequest().getHeader(CommonConstant.BGG_ROUTE);
                if (StringUtils.isNotBlank(bggRoute)) {
                    requestTemplate.header(CommonConstant.BGG_ROUTE, bggRoute);
                }
            }
        }
    }
}
