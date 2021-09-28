package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.observeraction;

/**
 * @ClassName UserControlller 用户注册，并给新用户发放体验金
 * （随着业务的扩展，后期可能会增加其他功能，使用观察者模式避免对 register 方法经常修改）
 * @Description
 * @Author guopy
 * @Date 2021/7/26 17:29
 */
public class UserControlller {
    //依赖注入
    private UserService userService;
    private PromotionService promotionService;

    public String register(String telphone, String password){
        String userId = userService.register(telphone, password);
        promotionService.issueNewUserExperienceCash(userId);
        return userId;
    }

}
