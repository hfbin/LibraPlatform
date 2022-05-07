package cn.hfbin.plugin.common.instance;

import cn.hfbin.plugin.common.constant.CommonConstant;
import cn.hfbin.plugin.common.enums.ServiceTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/5/4 2:34 下午
 * @description: 获取注册中心服务实现
 */
public class ServiceInstanceResourceImpl implements ServiceInstanceResource {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public List<String> getAllServiceList() {
        return discoveryClient.getServices();
    }

    @Override
    public List<String> getServiceList() {
       return getServiceByType(ServiceTypeEnum.SERVICE.getName());
    }


    @Override
    public List<String> getGatewayList() {
        return getServiceByType(ServiceTypeEnum.GATEWAY.getName());
    }


    private List<String> getServiceByType(String type) {
        List<String> gatewayList = new ArrayList<>();
        List<String> services = this.getAllServiceList();
        for (String service : services) {
            List<ServiceInstance> instances = this.getServiceInstanceList(service);
            for (ServiceInstance instance : instances) {
                Map<String, String> metadata = instance.getMetadata();
                String serviceId = instance.getServiceId().toLowerCase();
                String serviceType = metadata.get(CommonConstant.SPRING_APPLICATION_TYPE);
                if (StringUtils.equals(serviceType, type)) {
                    if (!gatewayList.contains(serviceId)) {
                        gatewayList.add(serviceId);
                    }
                }
            }
        }
        return gatewayList;
    }


    @Override
    public List<ServiceInstance> getServiceInstanceList(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }
}
