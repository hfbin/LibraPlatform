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

package cn.hfbin.gateway.exception;

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.core.exception.CommonExceptionCode;
import cn.hfbin.common.core.exception.LibraException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author: huangfubin
 * @Description: JsonExceptionHandler 异常处理类
 * @Date: 2021/7/9
 */
@Slf4j
public class JsonExceptionHandler implements ErrorWebExceptionHandler {

    private final static String HTTP_STATUS = "httpStatus";
    private final static String BODY = "body";

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 存储处理异常后的信息
     */
    private final Map<String, Object> exceptionResult = new ConcurrentHashMap<>(6);

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    /**
     * 异常拦截处理
     * @param exchange exchange
     * @param ex e
     * @return Mono
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // 按照异常类型进行处理
        HttpStatus httpStatus;
        String body;
        int code;
        if(ex instanceof LibraException){
            LibraException libraException = (LibraException) ex;
            httpStatus = HttpStatus.OK;
            code = libraException.getServiceCode().getCode();
            body = libraException.getServiceCode().getMsg();
        } else if (ex instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            code =  CommonExceptionCode.SYSTEM_502.getCode();
            body = "[Gateway Handler Exception] Service Not Found";
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            httpStatus = responseStatusException.getStatus();
            code =  CommonExceptionCode.SYSTEM_502.getCode();
            body = responseStatusException.getMessage();
        } else{
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            code =  CommonExceptionCode.SYSTEM_502.getCode();
            body = "[Gateway Handler Exception] Internal Server Error";
        }
        //封装响应体
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put(HTTP_STATUS, httpStatus);
        ResponseData<String> error = ResponseData.error(code, body);
        // 重新返回自定义的数据
        result.put(BODY, JSONObject.toJSON(error));
        ServerHttpRequest request = exchange.getRequest();
        log.error("网关拦截异常:{},记录异常信息:{}", request.getPath(), error);
        //参考AbstractErrorWebExceptionHandler
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        exceptionResult.putAll(result);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));

    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        return ServerResponse.status((HttpStatus) exceptionResult.get(HTTP_STATUS))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(exceptionResult.get(BODY)));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return JsonExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return JsonExceptionHandler.this.viewResolvers;
        }

    }

}
