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

package cn.hfbin.bgg.gateway.processor;

import cn.hfbin.bgg.common.adapter.PluginAdapter;
import cn.hfbin.bgg.common.constant.CommonConstant;
import cn.hfbin.bgg.common.nacos.processor.NacosListenerProcessor;
import cn.hfbin.bgg.common.processor.BggRouteNacosProcessor;
import cn.hfbin.bgg.gateway.route.GatewayRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/2/01 11:10 下午
 * @description:
 */
public class GatewayRouteNacosProcessor extends NacosListenerProcessor {
    private static final Logger log = LoggerFactory.getLogger(BggRouteNacosProcessor.class);

    @Autowired
    private PluginAdapter pluginAdapter;
    @Autowired
    private GatewayRoute gatewayRoute;

    @Override
    public String getGroup() {
        return CommonConstant.DYNAMIC_ROUTE_KEY;
    }

    @Override
    public String getDataId() {
        return pluginAdapter.getServiceId() + "-" + CommonConstant.DYNAMIC_ROUTE_KEY;
    }

    @Override
    public void callbackConfig(String config) {
        log.info("nacos listener dataId -> {}", getDataId());
        log.info("config info  -> {}", config);
        gatewayRoute.updateAll(config);
    }
}
