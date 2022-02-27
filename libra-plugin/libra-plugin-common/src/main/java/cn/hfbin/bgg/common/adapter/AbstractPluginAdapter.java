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
import cn.hfbin.bgg.common.constant.CommonConstant;
import cn.hfbin.bgg.common.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

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
        ruleCache.put(CommonConstant.LOCAL_RULE, rule);
    }

    @Override
    public Rule getLocalRule() {
        return ruleCache.get(CommonConstant.LOCAL_RULE);
    }

    @Override
    public void setRemoteRule(Rule rule) {
        ruleCache.put(CommonConstant.REMOTE_RULE, rule);
    }

    @Override
    public Rule getRemoteRule() {
        return ruleCache.get(CommonConstant.REMOTE_RULE);
    }

    @Override
    public Rule getRule() {
        Rule remoteRule = getRemoteRule();
        if (Objects.nonNull(remoteRule)) {
            return remoteRule;
        }
        return getLocalRule();
    }
}
