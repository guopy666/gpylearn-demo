package com.gpy.designpatterns.observerdesignpattern.eventbus.googleeventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.gpy.designpatterns.observerdesignpattern.observeraction.RegObserver;
import com.gpy.designpatterns.observerdesignpattern.observeraction.UserService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @ClassName EventBusUserController
 * @Description
 * @Author guopy
 * @Date 2021/7/26 18:06
 */
public class EventBusUserController {

    //依赖注入
    private UserService userService;
    private EventBus eventBus;
    private static final Integer DEFAULT_EVENTBUS_THREAD_POOL_SIZE = 20;

    public EventBusUserController(){
        // eventBus = new EventBus();  //同步阻塞模式

        //异步非阻塞模式
        eventBus = new AsyncEventBus(Executors.newFixedThreadPool(DEFAULT_EVENTBUS_THREAD_POOL_SIZE));
    }

    public void setObservers(List<RegObserver> regObservers){
        for (RegObserver regObserver : regObservers) {
            //注册观察者，可以接收任意类型的观察者
            eventBus.register(regObserver);
        }
    }

    public String register(String telphone, String password){
        String userId = userService.register(telphone, password);
        //用来给观察者发送消息(post方法支持发送给匹配上的观察者，不同消息类型的定义通过 @SubScribe 实现)
        eventBus.post(userId);
        return userId;
    }

}
