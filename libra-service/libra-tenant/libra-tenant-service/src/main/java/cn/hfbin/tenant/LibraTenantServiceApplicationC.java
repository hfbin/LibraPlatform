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
public class LibraTenantServiceApplicationC {

	public static void main(String[] args) {
		System.setProperty("server.port", "7830");
		System.setProperty("spring.cloud.nacos.discovery.metadata.version", "2.0");

		SpringApplication.run(LibraTenantServiceApplicationC.class, args);
	}

}
