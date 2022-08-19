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

package cn.hfbin.plugin.nacos.processor;

import cn.hfbin.plugin.common.constant.CommonConstant;
import cn.hfbin.plugin.nacos.enums.NacosFormatType;
import cn.hfbin.plugin.nacos.operation.NacosOperation;
import cn.hfbin.plugin.common.thread.ThreadPoolFactory;
import com.alibaba.nacos.api.config.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/2/01 10:04 下午
 * @description:
 */
public abstract class NacosListenerProcessor extends AbstractConfigProcessor {
    private final ExecutorService executorService = ThreadPoolFactory.getExecutorService("nacos-config");
    private static final Logger log = LoggerFactory.getLogger(NacosListenerProcessor.class);

    @Autowired
    private NacosOperation nacosOperation;

    @Autowired
    private Environment environment;

    private Listener listener;


    @PostConstruct
    public void initialize() {
        String group = getGroup();
        String dataId = getDataId();
        try {
            listener = nacosOperation.subscribeConfig(group, dataId, executorService, this::callbackConfig);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("add nacos config listener error");
        }
        try {
            String config = nacosOperation.getConfig(group, dataId);
            if (config != null) {
                callbackConfig(config);
            } else {
                log.warn("get nacos config is null");
                boolean initConfig = Boolean.parseBoolean(environment.getProperty(CommonConstant.LIBRA_NACOS_CONFIG_INIT));
                if(initConfig){
                    nacosOperation.publishConfig(group, dataId, getDefaultConfig(), NacosFormatType.JSON_FORMAT);
                    log.info("create nacos config success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get nacos config error");
        }
    }

    @Override
    public void destroy() {
        if (listener == null) {
            return;
        }
        log.info("NacosListenerProcessor destroy");
        String group = getGroup();
        String dataId = getDataId();
        try {
            nacosOperation.unsubscribeConfig(group, dataId, listener);
        } catch (Exception e) {
            e.printStackTrace();
            executorService.shutdownNow();
        }
    }

}
