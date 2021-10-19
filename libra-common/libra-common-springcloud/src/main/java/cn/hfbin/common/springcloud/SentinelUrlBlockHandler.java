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

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.core.api.ServiceCode;
import cn.hfbin.common.core.exception.CommonExceptionCode;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huangfubin
 * @Description: Sentinel 流控、降级、参数热点、系统、授权 统一拦截处理
 * @Date: 2021/9/25
 */
@Slf4j
@Configuration
public class SentinelUrlBlockHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        ServiceCode serviceCode = CommonExceptionCode.SYSTEM_503;
        // 流控（限流）
        if(e instanceof FlowException) {
            log.error("流控 FlowException", e);
            serviceCode = CommonExceptionCode.SENTINEL_FLOW;
        }
        // 降级
        if(e instanceof DegradeException){
            log.error("降级 DegradeException", e);
            serviceCode = CommonExceptionCode.SENTINEL_DEGRADE_FLOW;
        }
        // 参数热点异常
        if(e instanceof ParamFlowException){
            log.error("参数热点 ParamFlowException", e);
            serviceCode = CommonExceptionCode.SENTINEL_PARAM_FLOW;
        }
        // 系统异常
        if(e instanceof SystemBlockException){
            log.error("系统 SystemBlockException", e);
            serviceCode = CommonExceptionCode.SENTINEL_SYSTEM_BLOCK;
        }
        // 授权异常
        if(e instanceof AuthorityException){
            log.error("授权 AuthorityException", e);
            serviceCode = CommonExceptionCode.SENTINEL_AUTHORITY;
        }

        // http状态码
        response.setStatus(429);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(ResponseData.error(serviceCode)));
    }
}
