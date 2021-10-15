package cn.hfbin.auth;

import cn.hfbin.auth.constant.AuthConstant;
import cn.hfbin.base.constant.BaseConstant;
import cn.hfbin.common.core.constant.LibraConstant;
import cn.hfbin.tenant.constant.TenantConstant;
import cn.hfbin.ucpm.constant.UcPmConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients({UcPmConstant.LIBRA_UCPM_PACKAGE, TenantConstant.LIBRA_TENANT_PACKAGE, BaseConstant.LIBRA_BASE_PACKAGE})
@ComponentScan({AuthConstant.LIBRA_AUTH_PACKAGE, LibraConstant.LIBRA_COMMON_PACKAGE, UcPmConstant.LIBRA_UCPM_PACKAGE, TenantConstant.LIBRA_TENANT_PACKAGE, BaseConstant.LIBRA_BASE_PACKAGE})
public class LibraAuthApplicationB {

	public static void main(String[] args) {
		System.setProperty("server.port", "7879");
		System.setProperty("spring.cloud.nacos.discovery.metadata.version", "2.0");
		SpringApplication.run(LibraAuthApplicationB.class, args);
	}

}
