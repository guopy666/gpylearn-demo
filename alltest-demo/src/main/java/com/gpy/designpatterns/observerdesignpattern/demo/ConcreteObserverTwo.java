package com.gpy.designpatterns.observerdesignpattern.demo;

/**
 * @ClassName ConcreteObserverTwo
 * @Description
 * @Author guopy
 * @Date 2021/7/26 16:32
 */
public class ConcreteObserverTwo implements Observer{
    @Override
    public void update() {
        System.out.println("ConcreteObserverTwo is notified!");
    }
}
