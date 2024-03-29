package cn.hfbin.ucpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LibraUcPmServiceApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "7827");
		System.setProperty("spring.cloud.nacos.discovery.metadata.version", "1.0");
		SpringApplication.run(LibraUcPmServiceApplication.class, args);
	}

}
