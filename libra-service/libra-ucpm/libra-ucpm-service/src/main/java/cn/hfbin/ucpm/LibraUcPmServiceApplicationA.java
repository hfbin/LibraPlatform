package cn.hfbin.ucpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LibraUcPmServiceApplicationA {

	public static void main(String[] args) {
		System.setProperty("server.port", "7821");
		System.setProperty("spring.cloud.nacos.discovery.metadata.version", "2.0");
		SpringApplication.run(LibraUcPmServiceApplicationA.class, args);
	}

}
