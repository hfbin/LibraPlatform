package cn.hfbin.bgg.service.context;

import org.springframework.web.context.request.RequestAttributes;

/**
 * @Author: huangfubin
 * @Description: ServiceStrategyContext 类
 * @Date: 2021/10/14
 */
public class ServiceStrategyContext {
    private static final ThreadLocal<ServiceStrategyContext> THREAD_LOCAL = ThreadLocal.withInitial(ServiceStrategyContext::new);

    private RequestAttributes requestAttributes;

    public static ServiceStrategyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    public RequestAttributes getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(RequestAttributes requestAttributes) {
        this.requestAttributes = requestAttributes;
    }
}
