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

package cn.hfbin.plugin.bgg.adapter;

import cn.hfbin.plugin.common.entity.Rule;
import cn.hfbin.plugin.common.adapter.PluginAdapter;

/**
 * @Author: huangfubin
 * @Description: PluginAdapter 类
 * @Date: 2021/10/14
 */
public interface BggPluginAdapter extends PluginAdapter {

    void setLocalRule(Rule rule);

    Rule getLocalRule();

    void setRemoteRule(Rule rule);

    Rule getRemoteRule();

    Rule getRule();
}
