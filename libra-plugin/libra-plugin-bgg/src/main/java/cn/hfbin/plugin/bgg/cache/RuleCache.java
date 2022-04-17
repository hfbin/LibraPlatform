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

package cn.hfbin.plugin.bgg.cache;

import cn.hfbin.plugin.bgg.entity.Rule;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @Author: huangfubin
 * @Description: RuleCache 灰度发布规则缓存
 * @Date: 2021/10/14
 */
public class RuleCache {
    private final LoadingCache<String, Rule> loadingCache;

    public RuleCache() {
        loadingCache = Caffeine.newBuilder()
                .expireAfterWrite(2 * 365 * 100, TimeUnit.DAYS)
                .initialCapacity(1)
                .maximumSize(10)
                .recordStats()
                .build(key -> null);
    }
    public boolean put(String key, Rule ruleEntity) {
        loadingCache.put(key, ruleEntity);
        return Boolean.TRUE;
    }
    public Rule get(String key) {
        try {
            return loadingCache.get(key);
        } catch (Exception e) {
            return null;
        }
    }
    public boolean clear(String key) {
        loadingCache.invalidate(key);
        return Boolean.TRUE;
    }
}
