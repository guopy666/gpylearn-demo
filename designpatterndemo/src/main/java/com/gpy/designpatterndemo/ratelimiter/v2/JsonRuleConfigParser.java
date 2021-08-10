package com.gpy.designpatterndemo.ratelimiter.v2;

import com.gpy.designpatterndemo.ratelimiter.RuleConfig;

import java.io.InputStream;

/**
 * @ClassName JsonRuleConfigParser
 * @Description
 * @Author guopy
 * @Date 2021/8/10 10:10
 */
public class JsonRuleConfigParser implements RuleConfigParser{
    @Override
    public RuleConfig parse(String configText) {
        return null;
    }

    @Override
    public RuleConfig parse(InputStream in) {
        return null;
    }
}
