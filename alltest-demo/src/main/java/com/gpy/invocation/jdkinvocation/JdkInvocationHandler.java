package com.gpy.invocation.jdkinvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**JDK 动态代理类使用步骤
 * 定义一个接口及其实现类；
 * 自定义 InvocationHandler 并重写invoke方法，在 invoke 方法中我们会调用原生方法（被代理类的方法）并自定义一些处理逻辑；
 * 通过 Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h) 方法创建代理对象；
 *
 * JDK 动态代理只能代理实现了接口的类
 */


/**
 * @ClassName JdkInvocationHandler
 * @Description
 * @Author guopy
 * @Date 2022/2/17 17:36
 */
public class JdkInvocationHandler implements InvocationHandler {

    // 代理类中的真实对象
    private final Object target;

    public JdkInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        // 调用方法前的操作
        System.out.println("before method ->" + method.getName());
        Object invoke = method.invoke(target, args);
        // 调用方法后的操作
        System.out.println("after method, "+ invoke.toString());
        return invoke;
    }
}
