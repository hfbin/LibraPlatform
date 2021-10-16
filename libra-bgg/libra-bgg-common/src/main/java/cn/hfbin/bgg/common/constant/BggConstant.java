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

package cn.hfbin.bgg.common.constant;

/**
 * @Author: huangfubin
 * @Description: BggConstant 类
 * @Date: 2021/10/13
 */
public interface BggConstant {

    // 配置入口类型
    String BGG_TYPE = "Bgg-Type";
    // 需要走的全链路规则
    String BGG_ROUTE = "Bgg-Route";

    String NACOS_WEIGHT = "nacos.weight";

    String VERSION = "version";

    String RULE = "rule";

    String CONFIG_PATH = "classpath:rule.json";

    String EXPRESSION_PREFIX = "H";
    String EXPRESSION_REGEX = "\\#" + EXPRESSION_PREFIX + "\\['\\S+'\\]";
    String EXPRESSION_SUB_PREFIX = "#" + EXPRESSION_PREFIX + "['";
    String EXPRESSION_SUB_SUFFIX = "']";
    String SPLIT_FH = ";";
    String SPLIT_DY = "=";

}
