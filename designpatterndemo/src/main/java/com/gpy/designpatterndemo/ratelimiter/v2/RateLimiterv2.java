package com.gpy.designpatterndemo.ratelimiter.v2;

import com.gpy.designpatterndemo.ratelimiter.RateLimiteAlg;
import com.gpy.designpatterndemo.ratelimiter.RateLimiteRule;
import com.gpy.designpatterndemo.ratelimiter.RuleConfig;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName RateLimiterv2
 * @Description 修改后的 RateLimiter v2 版本
 * @Author guopy
 * @Date 2021/8/6 14:19
 */
@Slf4j
public class RateLimiterv2 {
    private ConcurrentHashMap<String, RateLimiteAlg> counters = new ConcurrentHashMap<>();
    private RateLimiteRule rule;

    public RateLimiterv2() {
        // 相比于第一版的改动，通过调用 RuleConfigSource 来实现配置加载
        RuleConfigSource source = new FileRuleConfigSource();
        RuleConfig ruleConfig = source.load();
        this.rule = new TrieRateLimitRule(ruleConfig);
    }

    public boolean limit(String appId, String url) throws InternalException {
        //...代码不变...
        return false;
    }
}
