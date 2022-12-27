package com.gpy.invocation.cglibinvocation;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName CglibMethodInterceptor
 * @Description
 * @Author guopy
 * @Date 2022/2/17 17:59
 */
public class CglibMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before cglib method " + method.getName());
        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("after cglib method, " + result.toString());
        return result;
    }
}
