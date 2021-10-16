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
