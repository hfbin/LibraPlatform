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

package cn.hfbin.bgg.common.adapter;

import cn.hfbin.bgg.common.cache.RuleCache;
import cn.hfbin.bgg.common.constant.BggConstant;
import cn.hfbin.bgg.common.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: huangfubin
 * @Description: AbstractPluginAdapter 类
 * @Date: 2021/10/14
 */
public abstract class AbstractPluginAdapter implements PluginAdapter{

    @Autowired
    protected RuleCache ruleCache;

    @Override
    public void setLocalRule(Rule rule) {
        ruleCache.put(BggConstant.RULE, rule);
    }

    @Override
    public Rule getLocalRule() {
        return ruleCache.get(BggConstant.RULE);
    }
}
