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

package cn.hfbin.common.core.config;

import cn.hfbin.common.core.context.HeaderCode;
import cn.hfbin.common.core.context.SpringContextUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangfubin
 * @Description: GlobalMvcConfigurer 类
 * @Date: 2021/9/8
 */
@Configuration
public class GlobalMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThreadLocalInterceptor())
                .addPathPatterns("/**")
                .order(-100);
    }

    /**
     * @Author: huangfubin
     * @Description: 处理异步线程请求头丢失问题
     * @Date: 2021/9/8
     */
    public static class ThreadLocalInterceptor implements HandlerInterceptor {

        /**
         * 拦截前处理
         * @param request request
         * @param response response
         * @param handler handler
         * @return
         * @throws Exception
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }
            Map<String, String> headers = new HashMap<>();
            headers.put(HeaderCode.CLIENT_CODE, request.getHeader(HeaderCode.CLIENT_CODE));
            headers.put(HeaderCode.TENANT_CODE, request.getHeader(HeaderCode.TENANT_CODE));
            headers.put(HeaderCode.IDENTITY_ID, request.getHeader(HeaderCode.IDENTITY_ID));
            headers.put(HeaderCode.IDENTITY_TYPE, request.getHeader(HeaderCode.IDENTITY_TYPE));

            // 放到TransmittableThreadLocal中，避免异步线程丢失
            SpringContextUtils.setLocalMap(headers);
            return true;
        }

        /**
         * DispatcherServlet完全处理完请求后,清除SpringContextUtils的LocalMap
         * @param request request
         * @param response response
         * @param handler handler
         * @param ex
         * @throws Exception
         */
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            SpringContextUtils.removeLocalMap();
        }
    }

}