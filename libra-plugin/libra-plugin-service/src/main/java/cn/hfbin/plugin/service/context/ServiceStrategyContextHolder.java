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

package cn.hfbin.plugin.service.context;

import cn.hfbin.plugin.bgg.context.AbstractStrategyContextHolder;
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
