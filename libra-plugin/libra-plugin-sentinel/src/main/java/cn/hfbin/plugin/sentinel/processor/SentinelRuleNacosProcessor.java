package cn.hfbin.plugin.sentinel.processor;

import cn.hfbin.plugin.nacos.processor.NacosListenerProcessor;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/4/18 12:27 上午
 * @description:
 */
public class SentinelRuleNacosProcessor extends NacosListenerProcessor{
    @Override
    public String getGroup() {
        return null;
    }

    @Override
    public String getDataId() {
        return null;
    }

    @Override
    public String getDefaultConfig() {
        return null;
    }

    @Override
    public void callbackConfig(String config) {

    }
}
