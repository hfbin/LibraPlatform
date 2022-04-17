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

package cn.hfbin.plugin.bgg.service;

import cn.hfbin.plugin.bgg.adapter.BggPluginAdapter;
import cn.hfbin.plugin.bgg.context.StrategyContextHolder;
import cn.hfbin.plugin.bgg.entity.*;
import cn.hfbin.plugin.common.constant.CommonConstant;
import cn.hfbin.plugin.bgg.weight.MapWeightRandom;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: huangfubin
 * @Description: StrategyContextService 类
 * @Date: 2021/10/14
 */
public class StrategyContextService {

    @Autowired
    private BggPluginAdapter pluginAdapter;

    @Autowired
    private StrategyContextHolder strategyContextHolder;

    private final Pattern pattern = Pattern.compile(CommonConstant.EXPRESSION_REGEX);

    /**
     * 执行优先级为: 蓝绿路由配置 -> 灰度路由配置 -> 全局兜底路由配置
     * @return 链路
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
     * @return 链路
     */
    private String getGlobalRouteVersion() {
        Rule localRule = pluginAdapter.getRule();
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
     * @return 链路
     */
    private String getConditionGrayRouteVersion() {
        String routeKey = null;
        Rule localRule = pluginAdapter.getRule();
        if(Objects.nonNull(localRule)){
            StrategyRelease strategyRelease = localRule.getStrategyRelease();
            if(Objects.nonNull(strategyRelease)){
                Gray gray = strategyRelease.getGray();
                if(Objects.nonNull(gray)){
                    List<Condition> conditionList = gray.getConditionList();
                    if(CollectionUtils.isNotEmpty(conditionList)){
                        Condition condition = this.matchingExpressionStrategyCondition(conditionList);
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
            return localRule.getRoutes().get(getRouteByWeightRandom(routeKey));
        }
        return null;
    }
    /**
     * 蓝绿规则匹配
     * @return 链路
     */
    private String getConditionBlueGreenRouteVersion() {
        String routeKey = null;
        Rule localRule = pluginAdapter.getRule();
        if(Objects.nonNull(localRule)){
            StrategyRelease strategyRelease = localRule.getStrategyRelease();
            if(Objects.nonNull(strategyRelease)){
                BlueGreen blueGreen = strategyRelease.getBlueGreen();
                if(Objects.nonNull(blueGreen)){
                    List<Condition> conditionList = blueGreen.getConditionList();
                    if(CollectionUtils.isNotEmpty(conditionList)){
                        Condition condition = this.matchingExpressionStrategyCondition(conditionList);
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

    /**
     *
     * @description  根据权重配比获取具体链路
     * @param routeKey routeKey
     * @author huangfubin
     * @date 2021/10/16
     * @return java.lang.String
     */

    private String getRouteByWeightRandom(String routeKey) {
        String[] routeKeys = routeKey.split(CommonConstant.SPLIT_FH);
        List<Pair<String, Integer>> weightList = new ArrayList<>();
        for (String key : routeKeys) {
            String[] route = key.split(CommonConstant.SPLIT_DY);
            weightList.add(new ImmutablePair<>(route[0], Integer.valueOf(route[1])));
        }
        return new MapWeightRandom<>(weightList).random();
    }

    /**
     *
     * @description 使用 spring sepl 获取匹配条件的Condition
     * @param conditionList
     * @author huangfubin
     * @date 2021/10/16
     * @return cn.hfbin.bgg.common.entity.Condition
     */
    private Condition matchingExpressionStrategyCondition(List<Condition> conditionList) {
        for (Condition condition : conditionList) {
            String expression = condition.getExpression();
            Map<String, String> map = this.getHeaderToMap(expression);
            StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
            standardEvaluationContext.setVariable(CommonConstant.EXPRESSION_PREFIX, map);
            SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
            Boolean bool = spelExpressionParser.parseExpression(expression).getValue(standardEvaluationContext, Boolean.class);
            if(null != bool && bool){
                return condition;
            }
        }
        return null;
    }

    /**
     *
     * @description 根据expression条件，获取条件在请求头对应的值
     * @param expression expression
     * @author huangfubin
     * @date 2021/10/16
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private Map<String, String> getHeaderToMap(String expression) {
        Map<String, String> map = new HashMap<>();
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            String group = matcher.group();
            String name = StringUtils.substringBetween(group, CommonConstant.EXPRESSION_SUB_PREFIX, CommonConstant.EXPRESSION_SUB_SUFFIX);
            String value = strategyContextHolder.getHeader(name);
            if (StringUtils.isNotBlank(value)) {
                map.put(name, value);
            }
        }
        return map;
    }
}
