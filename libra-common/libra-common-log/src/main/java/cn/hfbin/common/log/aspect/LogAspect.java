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
 * @description 操作日志使用spring event异步入库
 * @date 2020/8/27
 */
@Slf4j
@Aspect
public class LogAspect {

    // 当前线程缓存
    private static final ThreadLocal<OptLogDto> THREAD_LOCAL_LOG = new ThreadLocal<>();

    // 错误截取长度
    private static final int ERROR_LENGTH = 10000;

    // 请求截取长度
    private static final int REQ_LENGTH = 5000;

    // 响应截取长度
    private static final int RES_LENGTH = 1000;

    // 是否放进db
    @Value("${" + ConfigValueConstant.LOG_DB + ":true}")
    private boolean dbEnabled;

    // 全局日志配置
    @Value("${" + ConfigValueConstant.LOG_ENABLED + ":true}")
    private boolean logEnabled;

    @Lazy
    @Autowired
    private OptLogServiceClient optLogServiceClient;


    /**
     * 扫描controller层下面所有方法和类切带有Log注解的
     */
    @Pointcut("execution(public * cn.hfbin.*.controller.*.*(..)) &&" +
            "@annotation(cn.hfbin.common.log.annotation.Log)")
    public void logAspect() {
    }

    /**
     * 执行之前处理
     *
     * @param joinPoint joinPoint
     */
    @Before(value = "logAspect()")
    public void recordLog(JoinPoint joinPoint) {
        if (!logEnabled) {
            return;
        }
        // 方法的注解
        Log log = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class);
        OptLogDto optLog = buildOptLog(joinPoint, log);
        // 将optLog设置到本地线程
        THREAD_LOCAL_LOG.set(optLog);
    }

    /**
     * 返回结果通知
     *
     * @param ret ret
     * @throws Throwable e
     */
    @AfterReturning(returning = "ret", pointcut = "logAspect()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        if (!logEnabled) {
            return;
        }
        // 方法的注解
        Log log = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class);
        ResponseData convert = Convert.convert(ResponseData.class, ret);
        // 本地线程获取optLog
        OptLogDto optLog = get();
        // 开启了记录返回数据
        if (log.response()) {
            optLog.setResInfo(StrUtil.sub(JSONObject.toJSONString(convert), 0, ERROR_LENGTH));
        }
        optLog.setResStatus(ResStatusEnum.SUCCESS.getCode());
        saveLog(optLog);
        // 移除本地线程
        THREAD_LOCAL_LOG.remove();
    }


    /**
     * 异常通知处理
     *
     * @param e e
     */
    @AfterThrowing(pointcut = "logAspect()", throwing = "e")
    public void doAfterThrowable(JoinPoint joinPoint, Throwable e) {
        if (!logEnabled) {
            return;
        }
        // 方法的注解
        Log log = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class);
        // 本地线程获取optLog
        OptLogDto optLog = get();
        // 开启了记录返回数据
        if (log.response()) {
            optLog.setResInfo(ExceptionUtil.stacktraceToString(e, RES_LENGTH));
        }
        optLog.setResStatus(ResStatusEnum.ERROR.getCode());
        saveLog(optLog);
        // 移除本地线程
        THREAD_LOCAL_LOG.remove();
    }

    /**
     * 获取THREAD_LOCAL_LOG
     *
     * @return 如果为空则new一个新的返回
     */
    private OptLogDto get() {
        OptLogDto sysLog = THREAD_LOCAL_LOG.get();
        // 如果为空则new一个新的返回
        if (sysLog == null) {
            return new OptLogDto();
        }
        return sysLog;
    }

    /**
     * 相关参数设置
     *
     * @param joinPoint joinPoint
     * @param log @Log
     * @return OptLogDto
     */
    private OptLogDto buildOptLog(JoinPoint joinPoint, Log log) {
        OptLogDto optLog = new OptLogDto();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // 开启了记录请求参数
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
        optLog.setUsername(StrUtil.isBlank(SpringContextUtils.getUsername()) ? "未登录" : SpringContextUtils.getUsername());
        optLog.setCreateTime(LocalDateTime.now());
        return optLog;
    }

    /**
     * 请求参数转json
     *
     * @param args args
     * @return json
     */
    private String getArgs(Object[] args) {
        Object[] params = Arrays.stream(args).filter(item -> !(item instanceof ServletRequest || item instanceof ServletResponse)).toArray();
        return JSONObject.toJSONString(params);
    }


    /**
     * 保异步保存日志
     * @param optLogDto
     */
    @Async("taskExecutor")
    public void saveLog(OptLogDto optLogDto) {
        if(dbEnabled){
            log.info("已开启日志入库：{}", optLogDto);
            OptLog optLog = new OptLog();
            BeanUtils.copyProperties(optLogDto, optLog);
            optLogServiceClient.save(optLog);
        }else {
            log.warn("未开启日志入库：{}", optLogDto);
        }
    }

}
