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

package cn.hfbin.common.core.context;

import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2020-03-30
 * @description: 请求上下文数据
 */
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * 多线程参数
     */
    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    /**
     * applicationContext
     * @param applicationContext applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 从applicationContext中获取Bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取applicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.isNull(requestAttributes) ? null :  requestAttributes.getRequest();
    }

    /**
     * 获取
     * @param key key
     * @return value
     */
    public static String get(String key) {
        Map<String, String> map = THREAD_LOCAL.get();
        return MapUtil.isEmpty(map) ? SpecialCharacterPool.EMPTY : Convert.toStr(map.get(key));
    }

    /**
     * 设置THREAD_LOCAL
     * @param localMap TransmittableThreadLocal
     */
    public static void setLocalMap(Map<String, String> localMap) {
        THREAD_LOCAL.set(localMap);
    }

    /**
     * 设置THREAD_LOCAL
     * @param key key
     * @param value value
     */
    public static void set(String key, String value) {
        Map<String, String> map = THREAD_LOCAL.get();
        if(MapUtil.isEmpty(map)){
            map = new ConcurrentHashMap<>(16);
            THREAD_LOCAL.set(map);
        }
        map.put(key, value == null ? SpecialCharacterPool.EMPTY : value);
    }

    /**
     * 移除THREAD_LOCAL
     */
    public static void removeLocalMap() {
        THREAD_LOCAL.remove();
    }


    /**
     * 获取当前用户ID
     *
     * @return ACCOUNT_ID
     */
    public static Long getAccountId() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String userId = Objects.isNull(httpServletRequest) ? get(HeaderCode.ACCOUNT_ID) : httpServletRequest.getHeader(HeaderCode.ACCOUNT_ID);
        return null == userId ? -1 :  Long.parseLong(userId);
    }

    /**
     * 获取当前用户的员工ID
     *
     * @return identityId
     */
    public static Long getIdentityId() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String identityId = Objects.isNull(httpServletRequest) ? get(HeaderCode.IDENTITY_ID) : httpServletRequest.getHeader(HeaderCode.IDENTITY_ID);
        return null == identityId ? -1 :  Long.parseLong(identityId);
    }

    /**
     * 获取当前用户的员工ID
     *
     * @return USERNAME
     */
    public static String getUsername() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return Objects.isNull(httpServletRequest) ? get(HeaderCode.USERNAME) : httpServletRequest.getHeader(HeaderCode.USERNAME);
    }

    /**
     * 获取公司数据范围
     *
     * @return DATA_SCOPE
     */
    public static Integer getDataScope() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return Integer.parseInt(Objects.isNull(httpServletRequest) ? get(HeaderCode.DATA_SCOPE) : httpServletRequest.getHeader(HeaderCode.DATA_SCOPE));
    }

    /**
     * 部门ID
     * @return DEPT_ID
     */
    public static Long getDeptId() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String orgId = Objects.isNull(httpServletRequest) ? get(HeaderCode.DEPT_ID) : httpServletRequest.getHeader(HeaderCode.DEPT_ID);
        return Long.valueOf(orgId);
    }

    /**
     * 部门code
     * @return DEPT_CODE
     */
    public static Long getDeptCode() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String deptCode = Objects.isNull(httpServletRequest) ? get(HeaderCode.DEPT_CODE) : httpServletRequest.getHeader(HeaderCode.DEPT_CODE);
        return Long.valueOf(deptCode);
    }

    /**
     * 获取请求来源
     *
     * @return IDENTITY_TYPE
     */
    public static Integer getIdentityType() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return Integer.valueOf(Objects.isNull(httpServletRequest) ? get(HeaderCode.IDENTITY_TYPE) : httpServletRequest.getHeader(HeaderCode.IDENTITY_TYPE));
    }

    /**
     * 获取该用户的token
     * @return TOKEN
     */
//    public static String getToken(){
//        HttpServletRequest httpServletRequest = getHttpServletRequest();
//        return Objects.isNull(httpServletRequest) ? get(HeaderCode.TOKEN) : httpServletRequest.getHeader(HeaderCode.TOKEN);
//    }

    /**
     * 获取该用户所在租户
     * @return TENANT_CODE
     */
    public static String getTenantCode(){
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return String.valueOf(Objects.isNull(httpServletRequest) ? get(HeaderCode.TENANT_CODE) : httpServletRequest.getHeader(HeaderCode.TENANT_CODE));
    }

    /**
     * 获取该用户所在租户
     * @return CLIENT_CODE
     */
    public static String getClientCode(){
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return String.valueOf(Objects.isNull(httpServletRequest) ? get(HeaderCode.CLIENT_CODE) : httpServletRequest.getHeader(HeaderCode.CLIENT_CODE));
    }

    /**
     * ip
     * @return IP
     */
    public static String getId(){
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return String.valueOf(Objects.isNull(httpServletRequest) ? get(HeaderCode.IP) : httpServletRequest.getHeader(HeaderCode.IP));
    }

    /**
     * ip
     * @return FEIGN
     */
    public static String getFeign(){
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return String.valueOf(Objects.isNull(httpServletRequest) ? get(HeaderCode.FEIGN) : httpServletRequest.getHeader(HeaderCode.FEIGN));
    }


}
