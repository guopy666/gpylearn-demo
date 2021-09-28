package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.observeraction;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ObserverUserControlller 用户注册，并给新用户发放体验金
 * @Description
 * @Author guopy
 * @Date 2021/7/26 17:29
 */
public class ObserverUserControlller {
    //依赖注入
    private UserService userService;

    private List<RegObserver> observers = new ArrayList<>();

    //一次性设置好，后续不用动态添加修改
    public void setObservers(List<RegObserver> observerList){
        observers.addAll(observerList);
    }

    public String register(String telphone, String password){
        String userId = userService.register(telphone, password);

        //同步阻塞
        for (RegObserver observer : observers) {
            observer.handleRegSuccess(userId);
        }
        return userId;

    }

}
