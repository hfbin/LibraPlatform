package cn.hfbin.gen;

import cn.hfbin.common.core.constant.LibraConstant;
import cn.hfbin.gen.constant.GenConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({GenConstant.LIBRA_GEN_PACKAGE, LibraConstant.LIBRA_COMMON_PACKAGE})
public class LibraGenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraGenServiceApplication.class, args);
	}

}
