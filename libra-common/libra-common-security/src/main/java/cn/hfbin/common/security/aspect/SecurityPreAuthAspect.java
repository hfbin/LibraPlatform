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

package cn.hfbin.common.security.aspect;

import cn.hfbin.common.core.constant.AuthRedisKeyConstant;
import cn.hfbin.common.core.constant.ConfigValueConstant;
import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.exception.CommonExceptionCode;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.redis.util.RedisUtil;
import cn.hfbin.common.security.annotation.PreAuthorize;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @Author: huangfubin
 * @Description: SecurityPreAuthAspect 类
 * @Date: 2021/8/4
 */
@Slf4j
@Aspect
@Component
public class SecurityPreAuthAspect {

    @Value("${" + ConfigValueConstant.SECURITY_ENABLED +":true}")
    private boolean securityEnabled;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 避免所有接口都进入切面，只有加了注解才会扫描
     */
    @Pointcut("@annotation(cn.hfbin.common.security.annotation.PreAuthorize) || " +
            "@within(cn.hfbin.common.security.annotation.PreAuthorize)"
    )
    public void PreAuthorizeCut() {
    }

    /**
     * 切面@PreAuthorize
     *
     * @param point point
     * @return Object
     * @throws Throwable e
     */
    @Around("PreAuthorizeCut()")
    public Object PreAuthorize(ProceedingJoinPoint point) throws Throwable {
        log.info("PreAuthorize-----------------------------------");
        if (!securityEnabled) {
            log.debug("接口权限校验已关闭");
            return point.proceed();
        }
        // 如果服务内部调用则放行
        if (this.checkFeign()) {
            return point.proceed();
        }
        // 方法的注解
        PreAuthorize methodPreAuthorize = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(PreAuthorize.class);
        log.info("方法上面内容 preAuth : {}", methodPreAuthorize);
        if (Objects.nonNull(methodPreAuthorize) && methodPreAuthorize.enabled() && checkPreAuthorize(methodPreAuthorize.value())) {
            throw new LibraException(CommonExceptionCode.SYSTEM_UNAUTHORIZED);
        }
        // 如果方法的注解未取到则取类注解
        if(Objects.isNull(methodPreAuthorize)){
            // 读取类的权限注解
            PreAuthorize classPreAuthorize = point.getTarget().getClass().getAnnotation(PreAuthorize.class);
            log.info("类上面内容 preAuth : {}", classPreAuthorize);
            if (Objects.nonNull(classPreAuthorize) && classPreAuthorize.enabled() && checkPreAuthorize(classPreAuthorize.value())) {
                throw new LibraException(CommonExceptionCode.SYSTEM_UNAUTHORIZED);
            }
        }
        return point.proceed();
    }

    /**
     * 权限校验
     *
     * @param authName 权限标识
     * @return true and false
     */
    private boolean checkPreAuthorize(String authName) {
        List<String> PreAuthorizeCodes = (List<String>) redisUtil.strGet(AuthRedisKeyConstant.USER_INTERFACE_KEY + SpringContextUtils.getClientCode() + SpecialCharacterPool.DOUBLE_COLON + SpringContextUtils.getIdentityId());
        if (CollectionUtil.isEmpty(PreAuthorizeCodes) || !PreAuthorizeCodes.contains(authName)) {
            return true;
        }
        return false;
    }

    /**
     * feign 调用不拦截
     *
     * @return true and false
     */
    private boolean checkFeign() {
        return StrUtil.isNotBlank(SpringContextUtils.getFeign()) && SpecialCharacterPool.TRUE.equals(SpringContextUtils.getFeign());
    }

}
