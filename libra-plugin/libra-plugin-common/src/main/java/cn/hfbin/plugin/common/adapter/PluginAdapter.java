package cn.hfbin.plugin.common.adapter;

import org.springframework.cloud.client.ServiceInstance;

import java.util.Map;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/4/16 11:43 下午
 * @description:
 */
public interface PluginAdapter {

    Map<String, String> getServerMetadata(ServiceInstance server);

    String getServerServiceId(ServiceInstance server);

    String getServiceId();
}
