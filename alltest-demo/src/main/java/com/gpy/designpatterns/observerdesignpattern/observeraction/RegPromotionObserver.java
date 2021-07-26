package com.gpy.designpatterns.observerdesignpattern.observeraction;

/**
 * @ClassName RegPromotionObserver 注册成功后，处理体验金发放的方法
 * @Description
 * @Author guopy
 * @Date 2021/7/26 17:39
 */
public class RegPromotionObserver implements RegObserver{
    @Override
    public void handleRegSuccess(String userId) {
        System.out.println("给 userId 发放了一个亿的体验金");
    }
}
