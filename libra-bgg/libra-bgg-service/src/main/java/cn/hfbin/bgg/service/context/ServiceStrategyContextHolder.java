package cn.hfbin.bgg.service.context;

import cn.hfbin.bgg.common.context.AbstractStrategyContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: huangfubin
 * @Description: ServiceStrategyContextHolder 类
 * @Date: 2021/10/14
 */
public class ServiceStrategyContextHolder extends AbstractStrategyContextHolder {

    public ServletRequestAttributes getRestAttributes() {
        RequestAttributes requestAttributes = ServiceStrategyContext.getCurrentContext().getRequestAttributes();
        if (requestAttributes == null) {
            requestAttributes = RequestContextHolder.getRequestAttributes();
        }

        return (ServletRequestAttributes) requestAttributes;
    }
    public HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = getRestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }
    @Override
    public String getHeader(String name) {
        String header = null;
        HttpServletRequest request = getHttpServletRequest();
        if (request != null) {
            header = request.getHeader(name);
        }
        return header;
    }

}
