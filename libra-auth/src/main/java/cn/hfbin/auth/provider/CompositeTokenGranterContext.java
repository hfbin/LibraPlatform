package cn.hfbin.auth.provider;

import cn.hfbin.auth.enums.AuthExceptionCode;
import cn.hfbin.auth.enums.GrantTypeEnum;
import cn.hfbin.common.core.exception.LibraException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: huangfubin
 * @Description: TokenGranterBuilder 类
 * @Date: 2021/7/29
 */
@Component
public class CompositeTokenGranterContext {

    private final Map<String, TokenGranterStrategy> granterMap = new ConcurrentHashMap<>();

    /**
     *
     * @description 初始化相关grantType
     * @param granterMap
     * @author huangfubin
     * @date 2021/7/29
     * @return
     */
    public CompositeTokenGranterContext(Map<String, TokenGranterStrategy> granterMap) {
        this.granterMap.putAll(granterMap);
    }

    /**
     *
     * @description 根据 grantType 获取对应实例
     * @param grantType
     * @author huangfubin
     * @date 2021/7/29
     * @return cn.hfbin.auth.provider.TokenGranter
     */
    public TokenGranterStrategy getGranter(GrantTypeEnum grantType) {
        TokenGranterStrategy tokenGranter = granterMap.get(grantType.getBeanName());
        Optional.ofNullable(tokenGranter).orElseThrow(()->new LibraException(AuthExceptionCode.GRANTER_INEXISTENCE));
        return tokenGranter;
    }

}
