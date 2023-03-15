package com.gpy.springalltong.redisdemo.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @ClassName JedisCofig
 * @Description
 * @Author guopy
 * @Date 2023/3/10 9:30
 */
public class JedisPoolCofig extends GenericObjectPoolConfig {
    public JedisPoolCofig(){
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }
}
