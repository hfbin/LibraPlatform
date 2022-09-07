package cn.hfbin.common.core.configuration;

import cn.hfbin.common.core.context.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicAutoConfiguration {
    @Bean
    public SpringContextUtils springContextUtils(){
        return new SpringContextUtils();
    }
}
