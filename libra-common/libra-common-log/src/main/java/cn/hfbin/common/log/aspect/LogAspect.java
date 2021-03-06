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

package cn.hfbin.common.log.aspect;


import cn.hfbin.base.client.OptLogServiceClient;
import cn.hfbin.base.entity.OptLog;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.core.constant.ConfigValueConstant;
import cn.hfbin.common.core.context.HeaderCode;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.log.annotation.Log;
import cn.hfbin.common.log.entity.OptLogDto;
import cn.hfbin.common.log.enums.ResStatusEnum;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author huangfubin
 * @description ??????????????????spring event????????????
 * @date 2020/8/27
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    // ??????????????????
    private static final ThreadLocal<OptLogDto> THREAD_LOCAL_LOG = new ThreadLocal<>();

    // ??????????????????
    private static final int ERROR_LENGTH = 10000;

    // ??????????????????
    private static final int REQ_LENGTH = 5000;

    // ??????????????????
    private static final int RES_LENGTH = 1000;

    // ????????????db
    @Value("${" + ConfigValueConstant.LOG_DB + ":true}")
    private boolean dbEnabled;

    // ??????????????????
    @Value("${" + ConfigValueConstant.LOG_ENABLED + ":true}")
    private boolean logEnabled;

    /**
     * ???????????????db?????????????????????EnableFeignClients?????????BaseConstant.LIBRA_BASE_PACKAGE??????
     */
    @Lazy
    @Autowired
    private OptLogServiceClient optLogServiceClient;


    /**
     * ??????controller????????????????????????????????????Log?????????
     */
    @Pointcut("execution(public * cn.hfbin.*.controller.*.*(..)) &&" +
            "@annotation(cn.hfbin.common.log.annotation.Log)")
    public void logAspect() {
    }

    /**
     * ??????????????????
     *
     * @param joinPoint joinPoint
     */
    @Before(value = "logAspect()")
    public void recordLog(JoinPoint joinPoint) {
        if (!logEnabled) {
            return;
        }
        // ???????????????
        Log log = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class);
        OptLogDto optLog = buildOptLog(joinPoint, log);
        // ???optLog?????????????????????
        THREAD_LOCAL_LOG.set(optLog);
    }

    /**
     * ??????????????????
     *
     * @param ret ret
     * @throws Throwable e
     */
    @AfterReturning(returning = "ret", pointcut = "logAspect()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        if (!logEnabled) {
            return;
        }
        // ???????????????
        Log log = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class);
        ResponseData convert = Convert.convert(ResponseData.class, ret);
        // ??????????????????optLog
        OptLogDto optLog = get();
        // ???????????????????????????
        if (log.response()) {
            optLog.setResInfo(StrUtil.sub(JSONObject.toJSONString(convert), 0, ERROR_LENGTH));
        }
        optLog.setResStatus(ResStatusEnum.SUCCESS.getCode());
        saveLog(optLog);
        // ??????????????????
        THREAD_LOCAL_LOG.remove();
    }


    /**
     * ??????????????????
     *
     * @param e e
     */
    @AfterThrowing(pointcut = "logAspect()", throwing = "e")
    public void doAfterThrowable(JoinPoint joinPoint, Throwable e) {
        if (!logEnabled) {
            return;
        }
        // ???????????????
        Log log = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class);
        // ??????????????????optLog
        OptLogDto optLog = get();
        // ???????????????????????????
        if (log.response()) {
            optLog.setResInfo(ExceptionUtil.stacktraceToString(e, RES_LENGTH));
        }
        optLog.setResStatus(ResStatusEnum.ERROR.getCode());
        saveLog(optLog);
        // ??????????????????
        THREAD_LOCAL_LOG.remove();
    }

    /**
     * ??????THREAD_LOCAL_LOG
     *
     * @return ???????????????new??????????????????
     */
    private OptLogDto get() {
        OptLogDto sysLog = THREAD_LOCAL_LOG.get();
        // ???????????????new??????????????????
        if (sysLog == null) {
            return new OptLogDto();
        }
        return sysLog;
    }

    /**
     * ??????????????????
     *
     * @param joinPoint joinPoint
     * @param log @Log
     * @return OptLogDto
     */
    private OptLogDto buildOptLog(JoinPoint joinPoint, Log log) {
        OptLogDto optLog = new OptLogDto();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // ???????????????????????????
        if (log.request()) {
            optLog.setReqParam(StrUtil.sub(getArgs(args), 0, REQ_LENGTH));
        }
        if(Objects.nonNull(request)){
            optLog.setIp(ServletUtil.getClientIP(request));
            optLog.setReqUrl(URLUtil.getPath(request.getRequestURI()));
            optLog.setHttpMethod(request.getMethod());
            optLog.setUserAgent(StrUtil.sub(request.getHeader(HeaderCode.USER_AGENT), 0, REQ_LENGTH));
        }
        optLog.setDescription(log.desc());
        optLog.setOptType(log.logType().getCode());
        optLog.setOptBehavior(log.optBehavior().getCode());
        optLog.setClientCode(SpringContextUtils.getClientCode());
        optLog.setTenantCode(SpringContextUtils.getTenantCode());
        optLog.setCreateBy(SpringContextUtils.getIdentityId());
        optLog.setUsername(StrUtil.isBlank(SpringContextUtils.getUsername()) ? "?????????" : SpringContextUtils.getUsername());
        optLog.setCreateTime(LocalDateTime.now());
        return optLog;
    }

    /**
     * ???????????????json
     *
     * @param args args
     * @return json
     */
    private String getArgs(Object[] args) {
        Object[] params = Arrays.stream(args).filter(item -> !(item instanceof ServletRequest || item instanceof ServletResponse)).toArray();
        return JSONObject.toJSONString(params);
    }


    /**
     * ?????????????????????
     * @param optLogDto
     */
    @Async("taskExecutor")
    public void saveLog(OptLogDto optLogDto) {
        if(dbEnabled){
            log.info("????????????????????????{}", optLogDto);
            OptLog optLog = new OptLog();
            BeanUtils.copyProperties(optLogDto, optLog);
            optLogServiceClient.save(optLog);
        }else {
            log.warn("????????????????????????{}", optLogDto);
        }
    }

}
