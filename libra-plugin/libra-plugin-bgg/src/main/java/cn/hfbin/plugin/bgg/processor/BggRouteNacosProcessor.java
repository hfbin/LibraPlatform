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

package cn.hfbin.plugin.bgg.processor;

import cn.hfbin.plugin.bgg.adapter.BggPluginAdapter;
import cn.hfbin.plugin.common.entity.Rule;
import cn.hfbin.plugin.common.constant.CommonConstant;
import cn.hfbin.plugin.nacos.processor.NacosListenerProcessor;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/2/01 11:10 下午
 * @description:
 */
public class BggRouteNacosProcessor extends NacosListenerProcessor {

    private static final Logger log = LoggerFactory.getLogger(BggRouteNacosProcessor.class);

    @Autowired
    private BggPluginAdapter pluginAdapter;

    @Override
    public String getGroup() {
        return CommonConstant.DYNAMIC_BGG_ROUTE_KEY;
    }

    @Override
    public String getDataId() {
        return pluginAdapter.getServiceId() + "-" + CommonConstant.DYNAMIC_BGG_ROUTE_KEY;
    }

    @Override
    public String getDefaultConfig() {
        return CommonConstant.DEFAULT_JSON_1;
    }

    @Override
    public void callbackConfig(String config) {
        log.info("nacos listener dataId [bgg-route] -> {}", getDataId());
        log.info("config info [bgg-route]  -> {}", config);
        if(StringUtils.isBlank(config) || (StringUtils.isNotBlank(config) && config.equals(CommonConstant.DEFAULT_JSON_1))){
            log.warn("config is null");
            return;
        }
        Rule rule = JSONObject.parseObject(config, Rule.class);
        if(Objects.isNull(rule)){
            log.warn("rule is null");
            return;
        }
        log.info("update bgg rule success");
        pluginAdapter.setRemoteRule(rule);
    }
}
