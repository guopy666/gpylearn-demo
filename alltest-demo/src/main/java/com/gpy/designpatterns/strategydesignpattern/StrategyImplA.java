package com.gpy.designpatterns.strategydesignpattern;

/**
 * @ClassName StrategyImplA
 * @Description
 * @Author guopy
 * @Date 2021/7/27 15:55
 */
public class StrategyImplA implements Strategy{
    @Override
    public void algorithmInterface() {
        System.out.println("this is implA");
    }
}
