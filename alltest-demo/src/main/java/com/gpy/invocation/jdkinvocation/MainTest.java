package com.gpy.invocation.jdkinvocation;

/**
 * @ClassName MainTest
 * @Description
 * @Author guopy
 * @Date 2022/2/17 17:40
 */
public class MainTest {
    public static void main(String[] args) {
        SmsService proxy = (SmsService)JdkProxyFactory.getProxy(new SmsServiceImpl());
        proxy.send("test jdk msg");
    }
}
