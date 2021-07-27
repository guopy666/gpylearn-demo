package com.gpy.designpatterns.strategydesignpattern;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName StrategyFactory
 * 对于无状态的策略类，没有成员变量，这样的类可以共享使用，可以事先创建好类缓存在工厂类中
 * @Description
 * @Author guopy
 * @Date 2021/7/27 16:05
 */
public class StrategyFactory {

    private static Map<String, Strategy>  map = new HashMap<>();

    static {
        map.put("A", new StrategyImplA());
        map.put("B", new StrategyImplB());
    }

    public static Strategy getStrategy(String type){
        if (type == null || type.isEmpty()){
            throw new IllegalArgumentException("type is null");
        }
        return map.get(type);
    }
}
