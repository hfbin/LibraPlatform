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

package cn.hfbin.plugin.common.constant;

/**
 * @Author: huangfubin
 * @Description: BggConstant 类
 * @Date: 2021/10/13
 */
public interface CommonConstant {

    String BGG_TYPE = "Bgg-Type";
    String BGG_ROUTE = "Bgg-Route";
    String NACOS_WEIGHT = "nacos.weight";
    String VERSION = "version";
    String LOCAL_RULE = "local-rule";
    String REMOTE_RULE = "remote-rule";
    String CONFIG_PATH = "classpath:rule.json";


    String LIBRA_BGG_ENABLED = "libra.bgg.enabled";
    String LIBRA_BGG_REMOTE_CONFIG_ENABLED = "libra.bgg.remote-config.enabled";
    String LIBRA_GATEWAY_ROUTE_ENABLED = "libra.gateway.dynamic-route.enabled";
    String LIBRA_NACOS_CONFIG_INIT = "libra.nacos-config-init";
    String SPRING_PROFILES_ACTIVE =  "spring.profiles.active";


    String EXPRESSION_PREFIX = "H";
    String EXPRESSION_REGEX = "\\#" + EXPRESSION_PREFIX + "\\['\\S+'\\]";
    String EXPRESSION_SUB_PREFIX = "#" + EXPRESSION_PREFIX + "['";
    String EXPRESSION_SUB_SUFFIX = "']";
    String SPLIT_FH = ";";
    String SPLIT_DY = "=";
    String DEFAULT_JSON = "{}";


    String DYNAMIC_ROUTE_KEY = "dynamic-route";
    String DYNAMIC_BGG_ROUTE_KEY = "bgg-dynamic-route";

}
