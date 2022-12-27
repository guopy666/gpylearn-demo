package com.gpy.invocation.jdkinvocation;

import java.lang.reflect.Proxy;

/**
 * @ClassName JdkProxyFactory
 * @Description 获取动态代理对象的工厂类
 * @Author guopy
 * @Date 2022/2/17 17:38
 */
public class JdkProxyFactory {
    public static Object getProxy(Object target){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),// 目标类的类加载器
                target.getClass().getInterfaces(),// 代理需要实现的接口，可以是多个
                new JdkInvocationHandler(target)// 代理对象对应的自定义 InvocationHandler
        );
    }
}
