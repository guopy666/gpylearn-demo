package com.gpy.designpatterndemo.ratelimiter.v2;

import com.gpy.designpatterndemo.ratelimiter.RuleConfig;

/**
 * @ClassName RuleConfigSource
 * @Description
 * @Author guopy
 * @Date 2021/8/6 14:23
 */
public interface RuleConfigSource {
    RuleConfig load();
}
