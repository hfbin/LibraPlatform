package cn.hfbin.bgg.common.service;

import cn.hfbin.bgg.common.adapter.PluginAdapter;
import cn.hfbin.bgg.common.context.StrategyContextHolder;
import cn.hfbin.bgg.common.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @Author: huangfubin
 * @Description: StrategyContextService 类
 * @Date: 2021/10/14
 */
public class StrategyContextService {

    @Autowired
    PluginAdapter pluginAdapter;

    @Autowired
    StrategyContextHolder strategyContextHolder;

    /**
     * 执行优先级为: 蓝绿路由配置 -> 灰度路由配置 -> 全局兜底路由配置
     * @return
     */
    public String getRouteVersion() {
        String routeVersion = getConditionBlueGreenRouteVersion();
        if (StringUtils.isEmpty(routeVersion)) {
            routeVersion = getConditionGrayRouteVersion();
            if (StringUtils.isEmpty(routeVersion)) {
                routeVersion = getGlobalRouteVersion();
            }
        }
        return routeVersion;
    }

    /**
     * 兜底规则匹配
     * @return
     */
    private String getGlobalRouteVersion() {
        Rule localRule = pluginAdapter.getLocalRule();
        if (Objects.nonNull(localRule)) {
            Strategy strategy = localRule.getStrategy();
            if (Objects.nonNull(strategy)) {
                String routeKey = strategy.getRouteKey();
                if (StringUtils.isNotBlank(routeKey)) {
                    return localRule.getRoutes().get(routeKey);
                }
            }
        }
        return null;
    }

    /**
     * 灰度规则匹配
     * @return
     */
    private String getConditionGrayRouteVersion() {
        String routeKey = null;
        Rule localRule = pluginAdapter.getLocalRule();
        if(Objects.nonNull(localRule)){
            StrategyRelease strategyRelease = localRule.getStrategyRelease();
            if(Objects.nonNull(strategyRelease)){
                Gray gray = strategyRelease.getGray();
                if(Objects.nonNull(gray)){
                    List<Condition> conditionList = gray.getConditionList();
                    if(CollectionUtils.isNotEmpty(conditionList)){
                        Condition condition = this.matchingExpressionStrategyConditionGray(conditionList);
                        if(Objects.nonNull(condition)){
                            routeKey = condition.getRouteKey();
                        }else {
                            routeKey = gray.getBasicCondition();

                        }
                    }
                }
            }
        }
        if(StringUtils.isNotBlank(routeKey)){
            return localRule.getRoutes().get(routeKey);
        }
        return null;
    }

    private Condition matchingExpressionStrategyConditionGray(List<Condition> conditionList) {
        return null;
    }

    /**
     * 蓝绿规则匹配
     * @return
     */
    private String getConditionBlueGreenRouteVersion() {
        String routeKey = null;
        Rule localRule = pluginAdapter.getLocalRule();
        if(Objects.nonNull(localRule)){
            StrategyRelease strategyRelease = localRule.getStrategyRelease();
            if(Objects.nonNull(strategyRelease)){
                BlueGreen blueGreen = strategyRelease.getBlueGreen();
                if(Objects.nonNull(blueGreen)){
                    List<Condition> conditionList = blueGreen.getConditionList();
                    if(CollectionUtils.isNotEmpty(conditionList)){
                        Condition condition = this.matchingExpressionStrategyConditionBlueGreen(conditionList);
                        if(Objects.nonNull(condition)){
                            routeKey = condition.getRouteKey();
                        }else {
                            routeKey = blueGreen.getBasicCondition();
                        }
                    }
                }
            }
        }
        if(StringUtils.isNotBlank(routeKey)){
            return localRule.getRoutes().get(routeKey);
        }
        return null;
    }

    private Condition matchingExpressionStrategyConditionBlueGreen(List<Condition> conditionList) {
        conditionList.forEach(data -> {
            String expression = data.getExpression();
        });
        return new Condition();
    }
}
