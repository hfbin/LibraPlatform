package cn.hfbin.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LibraAuthApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "7877");
		System.setProperty("spring.cloud.nacos.discovery.metadata.version", "1.0");
		SpringApplication.run(LibraAuthApplication.class, args);
	}

}
