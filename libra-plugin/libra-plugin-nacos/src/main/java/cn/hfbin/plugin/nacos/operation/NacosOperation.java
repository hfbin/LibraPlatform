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
package cn.hfbin.plugin.nacos.operation;

import java.util.concurrent.Executor;
import cn.hfbin.plugin.nacos.constant.NacosConstant;
import cn.hfbin.plugin.nacos.enums.NacosFormatType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
/**
 * @Author: huangfubin
 * @Description:
 * @Date: 2022/01/26
 */
public class NacosOperation implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(NacosOperation.class);

    @Autowired
    private ConfigService configService;

    public String getConfig(String group, String serviceId) throws NacosException {
        return configService.getConfig(serviceId, group, NacosConstant.NACOS_DEFAULT_TIMEOUT);
    }

    public boolean removeConfig(String group, String serviceId) throws NacosException {
        return configService.removeConfig(serviceId, group);
    }

    public boolean publishConfig(String group, String serviceId, String config) throws NacosException {
        return configService.publishConfig(serviceId, group, config);
    }

    public boolean publishConfig(String group, String serviceId, String config, NacosFormatType formatType) throws NacosException {
        return configService.publishConfig(serviceId, group, config, formatType.toString());
    }

    public Listener subscribeConfig(String group, String serviceId, Executor executor, NacosSubscribeCallback nacosSubscribeCallback) throws NacosException {
        Listener listener = new Listener() {
            @Override
            public void receiveConfigInfo(String config) {
                nacosSubscribeCallback.callback(config);
            }

            @Override
            public Executor getExecutor() {
                return executor;
            }
        };

        configService.addListener(serviceId, group, listener);

        return listener;
    }

    public void unsubscribeConfig(String group, String serviceId, Listener listener) {
        configService.removeListener(serviceId, group, listener);
    }

    @Override
    public void destroy() throws Exception {
        configService.shutDown();

        LOG.info("Shutting down Nacos config service...");
    }
}
