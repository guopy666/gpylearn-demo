package com.gpy.invocation.proxy;

/**
 *静态代理实现步骤:
 * 定义一个接口及其实现类；
 * 创建一个代理类同样实现这个接口 将目标对象注入进代理类，然后在代理类的对应方法调用目标类中的对应方法。
 * 这样就可以通过代理类屏蔽对目标对象的访问，并且可以在目标方法执行前后做一些自己想做的事情。
 *
 **/

/**
 * @ClassName SmsServiceProxy
 * @Description
 * @Author guopy
 * @Date 2022/2/17 16:19
 */
public class SmsServiceProxy implements SmsService{

    private final SmsService smsService;

    public SmsServiceProxy(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    public String send(String msg) {
        // 代理类的其他操作
        System.out.println("before send msg, print msg:" + msg);
        smsService.send(msg);
        // 调用方法后的其他操作
        System.out.println("after send msg, print msg:"+ msg);
        return null;
    }
}
