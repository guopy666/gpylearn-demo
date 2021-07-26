package com.gpy.designpatterns.observerdesignpattern.observeraction;

/**
 * @ClassName RegNotificationObserver 注册成功后发送消息给用户
 * @Description
 * @Author guopy
 * @Date 2021/7/26 17:41
 */
public class RegNotificationObserver implements RegObserver{

    @Override
    public void handleRegSuccess(String userId) {
        System.out.println("用户注册成功");
    }
}
