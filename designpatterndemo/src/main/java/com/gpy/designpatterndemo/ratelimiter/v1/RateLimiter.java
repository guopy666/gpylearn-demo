package com.gpy.designpatterndemo.ratelimiter.v1;

import com.gpy.designpatterndemo.ratelimiter.ApiLimit;
import com.gpy.designpatterndemo.ratelimiter.RateLimiteAlg;
import com.gpy.designpatterndemo.ratelimiter.RateLimiteRule;
import com.gpy.designpatterndemo.ratelimiter.RuleConfig;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName RateLimiter
 * @Description
 * @Author guopy
 * @Date 2021/8/4 15:10
 */
@Slf4j
public class RateLimiter {

    private RateLimiteRule rateLimiterRule;

    // 为每个 api 在内存中存储一个计数器
    private ConcurrentHashMap<String, RateLimiteAlg> counters = new ConcurrentHashMap<String, RateLimiteAlg>();

    public RateLimiter() {
        // 读取 yml 文件的配置规则
        InputStream in = null;
        RuleConfig ruleConfig = null;
        try {
            in = this.getClass().getResourceAsStream("/ratelimiter.yml");
            if (null != in) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(in, RuleConfig.class);
            }
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("in close error, e:{}", e.getMessage());
                }
            }
        }
        this.rateLimiterRule = new RateLimiteRule(ruleConfig);
    }

    public boolean limit(String appId, String url) {
        ApiLimit limit = rateLimiterRule.getLimit(appId, url);

        if (null == limit) {
            return true;
        }

        String counterKey = appId + ":" + limit.getApi();
        RateLimiteAlg rateLimiteAlg = counters.get(counterKey);

        if (null == rateLimiteAlg) {
            RateLimiteAlg newRateLimiteAlg = new RateLimiteAlg(limit.getLimit());
            rateLimiteAlg = counters.putIfAbsent(counterKey, newRateLimiteAlg);
            if (rateLimiteAlg == null) {
                rateLimiteAlg = newRateLimiteAlg;
            }
        }
        return rateLimiteAlg.tryAcquire();
    }

}
