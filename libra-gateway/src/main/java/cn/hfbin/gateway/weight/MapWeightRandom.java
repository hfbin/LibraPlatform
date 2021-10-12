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

package cn.hfbin.gateway.weight;

import com.alibaba.nacos.common.utils.MapUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author: huangfubin
 * @Description: MapWeightRandom 类
 * @Date: 2021/9/17
 */
public class MapWeightRandom <K, V extends Number>{

    private final TreeMap<Double, K> weightMap = new TreeMap<>();

    public MapWeightRandom(List<Pair<K, V>> pairList) {
        for (Pair<K, V> pair : pairList) {
            double value = pair.getValue().doubleValue();
            if (value <= 0) {
                continue;
            }
            double lastWeight = weightMap.size() == 0 ? 0 : weightMap.lastKey();
            weightMap.put(value + lastWeight, pair.getKey());
        }
    }

    public K random() {
        if (MapUtils.isEmpty(weightMap)) {
            return null;
        }
        double randomWeight = weightMap.lastKey() * Math.random();
        SortedMap<Double, K> tailMap = weightMap.tailMap(randomWeight, false);
        return weightMap.get(tailMap.firstKey());
    }
}
