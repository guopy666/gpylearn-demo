package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.demo;

/**
 * @ClassName ConcreteObserverOne
 * @Description
 * @Author guopy
 * @Date 2021/7/26 16:31
 */
public class ConcreteObserverOne implements Observer{


    @Override
    public void update() {
        System.out.println("ConcreteObserverOne is notified!");
    }
}
