package cn.hfbin.base;

import cn.hfbin.base.constant.BaseConstant;
import cn.hfbin.common.core.constant.LibraConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableDiscoveryClient
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({BaseConstant.LIBRA_BASE_PACKAGE,LibraConstant.LIBRA_COMMON_PACKAGE})
public class LibraBaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraBaseServiceApplication.class, args);
	}

}
