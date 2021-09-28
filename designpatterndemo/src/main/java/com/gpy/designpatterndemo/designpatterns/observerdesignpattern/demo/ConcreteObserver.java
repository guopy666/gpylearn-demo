package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ConcreteObserver
 * @Description
 * @Author guopy
 * @Date 2021/7/26 16:29
 */
public class ConcreteObserver implements Subject{
    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
