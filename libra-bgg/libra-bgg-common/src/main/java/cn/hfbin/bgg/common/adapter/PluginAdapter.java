package cn.hfbin.bgg.common.adapter;

import cn.hfbin.bgg.common.entity.Rule;

/**
 * @Author: huangfubin
 * @Description: PluginAdapter 类
 * @Date: 2021/10/14
 */
public interface PluginAdapter {


    void setLocalRule(Rule rule);

    Rule getLocalRule();

}
