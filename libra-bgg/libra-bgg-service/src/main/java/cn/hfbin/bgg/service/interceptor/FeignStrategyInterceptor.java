package cn.hfbin.bgg.service.interceptor;

import cn.hfbin.bgg.common.constant.BggConstant;
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
