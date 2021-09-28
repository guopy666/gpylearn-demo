package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.eventbus.myeventbus;

import java.util.concurrent.Executor;

/**
 * @ClassName MyAsyncEventBus
 * @Description
 * @Author guopy
 * @Date 2021/7/27 10:26
 */
public class MyAsyncEventBus extends MyEventBus {

    public MyAsyncEventBus(Executor executor){
        super(executor);
    }

}
