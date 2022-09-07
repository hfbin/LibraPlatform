package cn.hfbin.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LibraGenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraGenServiceApplication.class, args);
	}

}
