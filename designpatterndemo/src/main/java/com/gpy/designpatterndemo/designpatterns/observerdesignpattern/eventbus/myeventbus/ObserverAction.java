package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.eventbus.myeventbus;

import com.google.common.base.Preconditions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName ObserverAction 用来表示@MySubscribe注解的方法，target表示观察者类，method表示方法
 * @Description
 * @Author guopy
 * @Date 2021/7/27 9:36
 */
public class ObserverAction {
    private Object target;
    private Method method;

    public ObserverAction(Object target, Method method) {
        this.target = Preconditions.checkNotNull(target);
        this.method = method;
        this.method.setAccessible(true);
    }

    public void execute(Object event) {
        try {
            method.invoke(target, event);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
