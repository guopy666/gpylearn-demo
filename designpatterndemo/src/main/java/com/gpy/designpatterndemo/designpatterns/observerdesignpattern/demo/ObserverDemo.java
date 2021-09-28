package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.demo;

/**
 * @ClassName ObserverDemo  同步阻塞的观察者模式
 * @Description
 * @Author guopy
 * @Date 2021/7/26 16:33
 */
public class ObserverDemo {
    public static void main(String[] args) {
        ConcreteObserver observer = new ConcreteObserver();
        observer.registerObserver(new ConcreteObserverOne());
        observer.registerObserver(new ConcreteObserverTwo());

        observer.notifyObservers();
    }
}
