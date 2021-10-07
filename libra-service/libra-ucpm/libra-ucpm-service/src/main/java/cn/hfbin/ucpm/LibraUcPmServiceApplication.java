package cn.hfbin.ucpm;

import cn.hfbin.base.constant.BaseConstant;
import cn.hfbin.ucpm.constant.UcPmConstant;
import cn.hfbin.common.core.constant.LibraConstant;
import cn.hfbin.tenant.constant.TenantConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients({TenantConstant.LIBRA_TENANT_PACKAGE, BaseConstant.LIBRA_BASE_PACKAGE})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({UcPmConstant.LIBRA_UCPM_PACKAGE,LibraConstant.LIBRA_COMMON_PACKAGE, BaseConstant.LIBRA_BASE_PACKAGE, TenantConstant.LIBRA_TENANT_PACKAGE})
public class LibraUcPmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraUcPmServiceApplication.class, args);
	}

}
