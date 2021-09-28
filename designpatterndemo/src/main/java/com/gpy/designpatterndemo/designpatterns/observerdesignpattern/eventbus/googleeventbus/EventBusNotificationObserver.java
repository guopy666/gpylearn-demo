package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.eventbus.googleeventbus;

import com.google.common.eventbus.Subscribe;
import com.gpy.designpatterndemo.designpatterns.observerdesignpattern.observeraction.NotificationService;

/**
 * @ClassName EventBusNotificationObserver
 * @Description
 * @Author guopy
 * @Date 2021/7/26 18:15
 */
public class EventBusNotificationObserver {

    private NotificationService notificationService;

    @Subscribe
    public void handleRegSuccess(String userId){
        notificationService.sendInboxMessage(userId);
    }

}
