package cn.hfbin.tenant;

import cn.hfbin.common.core.constant.LibraConstant;
import cn.hfbin.tenant.constant.TenantConstant;
import cn.hfbin.ucpm.constant.UcPmConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients({UcPmConstant.LIBRA_UCPM_PACKAGE})
@SpringBootApplication
@ComponentScan({LibraConstant.LIBRA_COMMON_PACKAGE, TenantConstant.LIBRA_TENANT_PACKAGE, UcPmConstant.LIBRA_UCPM_PACKAGE})
public class LibraTenantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraTenantServiceApplication.class, args);
	}

}
