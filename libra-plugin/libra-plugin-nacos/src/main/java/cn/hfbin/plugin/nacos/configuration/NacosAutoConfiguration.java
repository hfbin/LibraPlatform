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

package cn.hfbin.plugin.nacos.configuration;

import cn.hfbin.plugin.common.constant.CommonConstant;
import cn.hfbin.plugin.nacos.constant.NacosConstant;
import cn.hfbin.plugin.nacos.operation.NacosOperation;
import cn.hfbin.plugin.common.util.PropertiesUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * @Author: huangfubin
 * @Description:
 * @Date: 2022/01/26
 */
@Configuration
public class NacosAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public NacosOperation nacosOperation() {
        return new NacosOperation();
    }

    @Bean
    public ConfigService configService() throws NacosException {
        Properties properties = createNacosProperties(environment, true);

        return NacosFactory.createConfigService(properties);
    }

    public static Properties createNacosProperties(Environment environment, boolean enableRemoteSyncConfig) {
        Properties properties = new Properties();
        PropertiesUtil.enrichProperties(properties, environment, NacosConstant.SPRING_CLOUD_NACOS_CONFIG_PREFIX, true, true);
        properties.put(NacosConstant.ENABLE_REMOTE_SYNC_CONFIG, Boolean.toString(enableRemoteSyncConfig));
        properties.put(NacosConstant.NAMESPACE, environment.getProperty(CommonConstant.SPRING_PROFILES_ACTIVE));
        return properties;
    }

}
