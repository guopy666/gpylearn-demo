package com.gpy.designpatterndemo.ratelimiter.v2;

import com.gpy.designpatterndemo.ratelimiter.RuleConfig;

import java.io.InputStream;

public interface RuleConfigParser {
    RuleConfig parse(String configText);
    RuleConfig parse(InputStream in);
}
