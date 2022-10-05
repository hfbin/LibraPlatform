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
import cn.hfbin.plugin.common.constant.CommonConstant;
import cn.hfbin.plugin.bgg.weight.MapWeightRandom;
import cn.hfbin.plugin.common.entity.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(StrategyContextService.class);

    @Autowired
    private BggPluginAdapter pluginAdapter;

    @Autowired
    private StrategyContextHolder strategyContextHolder;

    private final Pattern pattern = Pattern.compile(CommonConstant.EXPRESSION_REGEX);

    /**
     * 执行优先级为: 蓝绿路由配置(条件 -> 兜底) -> 灰度路由配置(条件 -> 兜底)
     * @return 链路
     */
    public String getRouteVersion() {
        String routeVersion = getConditionBlueGreenRouteVersion();
        if (StringUtils.isEmpty(routeVersion)) {
            routeVersion = getConditionGrayRouteVersion();
        }
        return routeVersion;
    }

    /**
     * 灰度规则匹配
     * @return 链路
     */
    private String getConditionGrayRouteVersion() {
        String routeKey = null;
        Rule rule = pluginAdapter.getRule();
        if(Objects.nonNull(rule)){
            StrategyRelease strategyRelease = rule.getStrategyRelease();
            if(Objects.nonNull(strategyRelease)){
                BlueGreenGray gray = strategyRelease.getGray();
                routeKey = getRouteKey(gray, CommonConstant.GRAY);
            }
        }
        if(StringUtils.isNotBlank(routeKey)){
            return JSONObject.toJSONString(rule.getRoutes().get(routeKey));
        }
        return null;
    }
    /**
     * 蓝绿规则匹配
     * @return 链路
     */
    private String getConditionBlueGreenRouteVersion() {
        String routeKey = null;
        Rule rule = pluginAdapter.getRule();
        if(Objects.nonNull(rule)){
            StrategyRelease strategyRelease = rule.getStrategyRelease();
            if(Objects.nonNull(strategyRelease)){
                BlueGreenGray blueGreen = strategyRelease.getBlueGreen();
                routeKey = getRouteKey(blueGreen, CommonConstant.BLUE_GREEN);
            }
        }
        if(StringUtils.isNotBlank(routeKey)){
            return JSONObject.toJSONString(rule.getRoutes().get(routeKey));
        }
        return null;
    }

    @SuppressWarnings("all")
    private String getRouteKey(BlueGreenGray gray, String type) {
        String routeKey = null;
        if(Objects.nonNull(gray)){
            List<Condition> conditionList = gray.getConditionList();
            if(CollectionUtils.isNotEmpty(conditionList)){
                Condition condition = this.matchingExpressionStrategyCondition(conditionList);
                if(Objects.nonNull(condition)){
                    if(CommonConstant.BLUE_GREEN.equals(type)){
                        routeKey = (String) condition.getRouteKey();
                    }else {
                        try {
                            Map<String, Integer> routeMap = (Map<String, Integer>) condition.getRouteKey();
                            routeKey = getRouteByWeightRandom(routeMap);
                        }catch (Exception e){
                            log.error("RouteKey ERROR");
                        }
                    }
                }else {
                    if(CommonConstant.BLUE_GREEN.equals(type)){
                        routeKey = (String) gray.getBasicRouteKey();
                    }else {
                        try {
                            Map<String, Integer> routeMap = (Map<String, Integer>) gray.getBasicRouteKey();
                            routeKey = getRouteByWeightRandom(routeMap);
                        }catch (Exception e){
                            log.error("RouteKey ERROR");
                        }
                    }
                }
            }
        }
        return routeKey;
    }

    /**
     *
     * @description  根据权重配比获取具体链路
     * @param routeKey routeKey
     * @author huangfubin
     * @date 2021/10/16
     * @return java.lang.String
     */

    private String getRouteByWeightRandom(Map<String, Integer> routeKey) {
        List<Pair<String, Integer>> weightList = new ArrayList<>();
        routeKey.forEach((k, v) -> weightList.add(new ImmutablePair<>(k, v)));
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
