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

package cn.hfbin.plugin.bgg.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: huangfubin
 * @Description: Rule 类
 * @Date: 2021/10/13
 */
public class Rule implements Serializable {
    private static final long serialVersionUID = -6294291383078484829L;
    private Strategy strategy;

    private StrategyRelease strategyRelease;

    private Map<String, String> routes = new LinkedHashMap<>();

    public Map<String, String> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, String> routes) {
        this.routes = routes;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public StrategyRelease getStrategyRelease() {
        return strategyRelease;
    }

    public void setStrategyRelease(StrategyRelease strategyRelease) {
        this.strategyRelease = strategyRelease;
    }

}
