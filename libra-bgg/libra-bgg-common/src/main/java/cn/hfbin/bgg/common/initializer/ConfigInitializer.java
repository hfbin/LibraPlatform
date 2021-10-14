package cn.hfbin.bgg.common.initializer;

import cn.hfbin.bgg.common.adapter.PluginAdapter;
import cn.hfbin.bgg.common.entity.Rule;
import cn.hfbin.bgg.common.loader.LocalConfigLoader;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @Author: huangfubin
 * @Description: ConfigInitializer 类
 * @Date: 2021/10/14
 */
public class ConfigInitializer {
    @Autowired
    private PluginAdapter pluginAdapter;
    @Autowired
    private LocalConfigLoader localConfigLoader;

    @PostConstruct
    public void initialize() {
        String config = null;
        try {
            config = localConfigLoader.getConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Rule rule = JSONObject.parseObject(config, Rule.class);
        pluginAdapter.setLocalRule(rule);
    }
}
