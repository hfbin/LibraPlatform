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

package cn.hfbin.plugin.common.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;

import java.util.Map;

/**
 * @Author: huangfubin
 * @Description: Adapter 类
 * @Date: 2021/10/14
 */
public class PluginAdapterImpl implements PluginAdapter {

    @Autowired
    protected Registration registration;

    @Override
    public Map<String, String> getServerMetadata(ServiceInstance server) {
        return server.getMetadata();
    }

    @Override
    public String getServerServiceId(ServiceInstance server) {
        return server.getServiceId();
    }

    @Override
    public String getServiceId() {
        return registration.getServiceId().toLowerCase();
    }
}
