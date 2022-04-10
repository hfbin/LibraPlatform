package cn.hfbin.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/4/10 2:13 下午
 * @description:
 */
@Getter
@Setter
@ConfigurationProperties("libra.gateway")
@Configuration
public class LibraGatewayProperties {

    /**
     *  不需要token认证路径
     */
    private Set<String> noIdentityPath;

    /**
     * 生产不可以操作接口
     */
    private Set<String> noOptPath;
}
