package com.gpy.invocation.proxy;

/**
 * @ClassName MainTest
 * @Description
 * @Author guopy
 * @Date 2022/2/17 16:22
 */
public class MainTest {
    public static void main(String[] args) {
        SmsService smsService = new SmsServiceImpl();
        SmsServiceProxy serviceProxy = new SmsServiceProxy(smsService);
        serviceProxy.send("test msg");
    }
}
