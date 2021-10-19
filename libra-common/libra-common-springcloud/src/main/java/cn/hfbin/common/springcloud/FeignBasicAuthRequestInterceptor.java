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

package cn.hfbin.common.springcloud;

import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.common.core.context.HeaderCode;
import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 *
 * Feign调用添加请求头
 *
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2020-03-16 23:56
 * @description:
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            // feign 请求标识
            requestTemplate.header(HeaderCode.FEIGN, SpecialCharacterPool.TRUE);
            String tenantCode = attributes.getRequest().getHeader(HeaderCode.TENANT_CODE);
            if (StrUtil.isNotBlank(tenantCode)) {
                requestTemplate.header(HeaderCode.TENANT_CODE, tenantCode);
            }
            String orgId = attributes.getRequest().getHeader(HeaderCode.DEPT_ID);
            if (StrUtil.isNotBlank(orgId)) {
                requestTemplate.header(HeaderCode.DEPT_ID, orgId);
            }
            String orgCode = attributes.getRequest().getHeader(HeaderCode.DEPT_CODE);
            if (StrUtil.isNotBlank(orgCode)) {
                requestTemplate.header(HeaderCode.DEPT_CODE, orgCode);
            }
            String userId = attributes.getRequest().getHeader(HeaderCode.IDENTITY_ID);
            if (StrUtil.isNotBlank(userId)) {
                requestTemplate.header(HeaderCode.IDENTITY_ID, userId);
            }
            String accountId = attributes.getRequest().getHeader(HeaderCode.ACCOUNT_ID);
            if (StrUtil.isNotBlank(accountId)) {
                requestTemplate.header(HeaderCode.ACCOUNT_ID, accountId);
            }
            String origin = attributes.getRequest().getHeader(HeaderCode.IDENTITY_TYPE);
            if (StrUtil.isNotBlank(origin)) {
                requestTemplate.header(HeaderCode.IDENTITY_TYPE, origin);
            }
            String dataScope = attributes.getRequest().getHeader(HeaderCode.DATA_SCOPE);
            if (StrUtil.isNotBlank(dataScope)) {
                requestTemplate.header(HeaderCode.DATA_SCOPE, dataScope);
            }
            String ip = attributes.getRequest().getHeader(HeaderCode.IP);
            if (StrUtil.isNotBlank(ip)) {
                requestTemplate.header(HeaderCode.IP, ip);
            }
            String clientCode = attributes.getRequest().getHeader(HeaderCode.CLIENT_CODE);
            if (StrUtil.isNotBlank(clientCode)) {
                requestTemplate.header(HeaderCode.CLIENT_CODE, clientCode);
            }
            String username = attributes.getRequest().getHeader(HeaderCode.USERNAME);
            if (StrUtil.isNotBlank(username)) {
                requestTemplate.header(HeaderCode.USERNAME, username);
            }
        }
    }

    @Bean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

}