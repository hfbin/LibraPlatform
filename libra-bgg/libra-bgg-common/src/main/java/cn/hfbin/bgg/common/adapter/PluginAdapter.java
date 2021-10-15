package cn.hfbin.bgg.common.adapter;

import cn.hfbin.bgg.common.entity.Rule;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Map;

/**
 * @Author: huangfubin
 * @Description: PluginAdapter 类
 * @Date: 2021/10/14
 */
public interface PluginAdapter {


    void setLocalRule(Rule rule);

    Rule getLocalRule();

    Map<String, String> getServerMetadata(ServiceInstance server);

    String getServerServiceId(ServiceInstance server);

}
