package cn.hfbin.bgg.common.adapter;

import org.springframework.cloud.client.ServiceInstance;

import java.util.Map;

/**
 * @Author: huangfubin
 * @Description: Adapter 类
 * @Date: 2021/10/14
 */
public class Adapter extends AbstractPluginAdapter{

    @Override
    public Map<String, String> getServerMetadata(ServiceInstance server) {
        return server.getMetadata();
    }

    @Override
    public String getServerServiceId(ServiceInstance server) {
        return server.getServiceId();
    }
}
