package com.gpy.designpatterndemo.ratelimiter.v2;

import com.gpy.designpatterndemo.ratelimiter.RateLimiteRule;
import com.gpy.designpatterndemo.ratelimiter.RuleConfig;

/**
 * @ClassName TrieRateLimitRule
 * @Description
 * @Author guopy
 * @Date 2021/8/10 10:04
 */
public class TrieRateLimitRule extends RateLimiteRule {
    public TrieRateLimitRule(RuleConfig ruleConfig) {
        super(ruleConfig);
    }

}
