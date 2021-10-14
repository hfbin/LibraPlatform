package cn.hfbin.bgg.common.cache;

import cn.hfbin.bgg.common.entity.Rule;
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
