package cn.hfbin.gateway.filter;

import cn.hfbin.common.core.context.HeaderCode;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.token.model.JwtUserInfo;
import cn.hfbin.gateway.constant.OrderConstant;
import cn.hfbin.gateway.enums.GatewayExceptionCode;
import cn.hfbin.gateway.util.ClientIp;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/4/10 2:11 下午
 * @description:
 */
@Slf4j
@Component
public class IdentityFilter extends BaseFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 租户
        String clientCode = exchange.getRequest().getHeaders().getFirst(HeaderCode.CLIENT_CODE);
        // 租户
        String tenantCode = exchange.getRequest().getHeaders().getFirst(HeaderCode.TENANT_CODE);
        // token
        String token = exchange.getRequest().getHeaders().getFirst(HeaderCode.TOKEN);
        // identityType
        String identityType = exchange.getRequest().getHeaders().getFirst(HeaderCode.IDENTITY_TYPE);

        log.info("请求路径:{}", exchange.getRequest().getPath());
        log.info("请求租户:{}", tenantCode);
        log.info("请求客户端:{}", clientCode);
        log.info("请求token:{}", token);
        log.info("请求身份类型:{}", identityType);

        //设置请求头
        Map<String, String> headers = new HashMap<>();

        if (!super.checkPath(super.getPath(exchange), gatewayProperties.getNoIdentityPath())) {
            if (StrUtil.isBlank(token)) {
                throw new LibraException(GatewayExceptionCode.TOKEN_EMPTY);
            }
            JwtUserInfo authInfo = authUtil.getAuthInfo(token);
            Optional.ofNullable(authInfo).orElseThrow(() -> new LibraException(GatewayExceptionCode.TOKEN_INVALID));
            headers.put(HeaderCode.ACCOUNT_ID, String.valueOf(authInfo.getAccountId()));
            headers.put(HeaderCode.IDENTITY_ID, String.valueOf(authInfo.getIdentityId()));
            headers.put(HeaderCode.DEPT_ID, String.valueOf(authInfo.getDeptId()));
            headers.put(HeaderCode.DEPT_CODE, authInfo.getDeptCode());
            headers.put(HeaderCode.USERNAME, authInfo.getUsername());
            headers.put(HeaderCode.DATA_SCOPE, String.valueOf(authInfo.getDeptCode()));
        }

        headers.put(HeaderCode.IDENTITY_TYPE, identityType);
        headers.put(HeaderCode.TENANT_CODE, tenantCode);
        headers.put(HeaderCode.CLIENT_CODE, clientCode);
        headers.put(HeaderCode.IP, ClientIp.getIp(exchange.getRequest()));
        setHeader(exchange, headers);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return OrderConstant.IDENTITY_ORDER;
    }
}
