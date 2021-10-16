/*
 *    Copyright [2021] [LibraPlatform of copyright huangfubin]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.hfbin.bgg.common.initializer;

import cn.hfbin.bgg.common.adapter.PluginAdapter;
import cn.hfbin.bgg.common.entity.Rule;
import cn.hfbin.bgg.common.loader.LocalConfigLoader;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Objects;

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
        if(Objects.nonNull(rule)){
            pluginAdapter.setLocalRule(rule);
        }
    }
}
