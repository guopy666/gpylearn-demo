package com.gpy.invocation.cglibinvocation;

import org.springframework.cglib.proxy.Enhancer;

/**
 * @ClassName CglibProxyFactory
 * @Description
 * @Author guopy
 * @Date 2022/2/17 18:01
 */
public class CglibProxyFactory {
    public static Object getProxy(Class<?> clazz){
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new CglibMethodInterceptor());
        // 创建代理类
        return enhancer.create();


    }
}
