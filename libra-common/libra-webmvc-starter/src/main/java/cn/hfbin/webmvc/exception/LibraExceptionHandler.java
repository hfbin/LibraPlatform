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

package cn.hfbin.webmvc.exception;


import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.core.exception.CommonExceptionCode;
import cn.hfbin.common.core.exception.LibraException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2020-03-30 17:33
 * @description: 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class LibraExceptionHandler {

    /**
     * 路径异常
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseData handlerNoFoundException(Exception e) {
        return ResponseData.error(CommonExceptionCode.SYSTEM_404);
    }

    /**
     * 路径异常
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public ResponseData feignException(HttpServletRequest req, HttpServletResponse rsp, FeignException e) {
        log.error("feignException [ HOST:{} URL:{} STATUS:{}] ", req.getRemoteHost(), req.getRequestURL(), rsp.getStatus(), e);
        return ResponseData.error(CommonExceptionCode.SYSTEM_502);
    }
    /**
     * 请求方式错误
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseData handlerHttpRequestMethodNotSupportedException(Exception e) {
        log.error("Request error : {}", e.getMessage());
        return ResponseData.error(CommonExceptionCode.HTTP_REQUEST_ERROR);
    }

    /**
     * 未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseData handleException(MissingServletRequestParameterException e) {
        log.error("Request params error: {}", e.getMessage(), e);
        return ResponseData.error(CommonExceptionCode.SYSTEM_501);
    }

    /**
     * 系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(LibraException.class)
    public ResponseData handleException(LibraException e) {
        log.error("LibraException:{} -- {} -- {}", e.getServiceCode().getCode(), e.getServiceCode().getMsg(), e);
        return ResponseData.error(e.getServiceCode());
    }
    /**
     * 未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseData handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseData.error(CommonExceptionCode.SYSTEM_500);
    }
}