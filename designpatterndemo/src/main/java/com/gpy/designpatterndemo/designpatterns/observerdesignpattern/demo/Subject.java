package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.demo;

public interface Subject {
    public void registerObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers();
}
