package cn.hfbin.plugin.common.instance;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/5/4 2:33 下午
 * @description: 获取注册中心服务接口定义
 */
public interface ServiceInstanceResource {

    List<String> getAllServiceList();

    List<String> getServiceList();

    List<String> getGatewayList();

    List<ServiceInstance> getServiceInstanceList(String serviceId);
}
