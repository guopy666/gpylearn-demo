package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.eventbus.myeventbus;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @ClassName MyEventBus
 * @Description
 * @Author guopy
 * @Date 2021/7/27 10:17
 */
public class MyEventBus {
    private Executor executor;
    private ObserverRegistry observerRegistry = new ObserverRegistry();

    public MyEventBus(){
        this(MoreExecutors.directExecutor());
    }

    public MyEventBus(Executor directExecutor) {
        this.executor = directExecutor;
    }

    public void register(Object object){
        observerRegistry.register(object);
    }

    public void post(Object event){
        List<ObserverAction> matchedObserverAction = observerRegistry.getMatchedObserverAction(event);
        for (ObserverAction observerAction : matchedObserverAction) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    observerAction.execute(event);
                }
            });
        }
    }
}
