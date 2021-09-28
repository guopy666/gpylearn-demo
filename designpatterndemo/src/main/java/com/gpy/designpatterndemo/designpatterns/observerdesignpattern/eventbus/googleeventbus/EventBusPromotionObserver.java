package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.eventbus.googleeventbus;

import com.google.common.eventbus.Subscribe;
import com.gpy.designpatterndemo.designpatterns.observerdesignpattern.observeraction.PromotionService;

/**
 * @ClassName EventBusPromotionObserver
 * @Description
 * @Author guopy
 * @Date 2021/7/26 18:13
 */
public class EventBusPromotionObserver {

    //依赖注入
    private PromotionService promotionService;

    //这个注解可以定义不同类型的消息类型
    @Subscribe
    public void handleRegSuccess(String userId){
        promotionService.issueNewUserExperienceCash(userId);
    }

}
