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
