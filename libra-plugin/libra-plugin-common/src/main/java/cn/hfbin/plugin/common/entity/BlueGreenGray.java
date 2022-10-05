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

package cn.hfbin.plugin.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: huangfubin
 * @Description: Rule 类
 * @Date: 2021/10/13
 */
public class BlueGreenGray implements Serializable {
    private static final long serialVersionUID = 8132971609944999016L;
    private List<Condition> conditionList;

    private Object basicRouteKey;

    public BlueGreenGray(List<Condition> conditionList, String basicCondition) {
        this.conditionList = conditionList;
        this.basicRouteKey = basicCondition;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public Object getBasicRouteKey() {
        return basicRouteKey;
    }

    public void setBasicRouteKey(Object basicRouteKey) {
        this.basicRouteKey = basicRouteKey;
    }
}
