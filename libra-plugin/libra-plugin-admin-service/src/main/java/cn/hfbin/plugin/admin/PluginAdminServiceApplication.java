package cn.hfbin.plugin.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/4/30 5:00 下午
 * @description:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PluginAdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PluginAdminServiceApplication.class, args);
    }
}
