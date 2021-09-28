package com.gpy.designpatterndemo.designpatterns.strategydesignpattern;

/**
 * @ClassName StrategyFactory2 对于有状态的策略类，需要每次返回新创建的策略对象
 * @Description
 * @Author guopy
 * @Date 2021/7/27 16:12
 */
public class StrategyFactory2 {

    public Strategy getStrategy(String type){
        if (type.equals("A")){
            return new StrategyImplA();
        } else if (type.equals("B")){
            return new StrategyImplB();
        }
        return null;
    }

}
