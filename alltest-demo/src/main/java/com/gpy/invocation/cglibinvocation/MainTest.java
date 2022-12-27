package com.gpy.invocation.cglibinvocation;

/**
 * @ClassName MainTest
 * @Description
 * @Author guopy
 * @Date 2022/2/17 18:03
 *
 *
 * JDK 动态代理和 CGLIB 动态代理对比
 * JDK 动态代理只能代理实现了接口的类或者直接代理接口，而 CGLIB 可以代理未实现任何接口的类。
 * 另外， CGLIB 动态代理是通过生成一个被代理类的子类来拦截被代理类的方法调用，因此不能代理声明为 final 类型的类和方法。
 * 就二者的效率来说，大部分情况都是 JDK 动态代理更优秀，随着 JDK 版本的升级，这个优势更加明显。
 *
 */
public class MainTest {
    public static void main(String[] args) {
        SmsService proxy = (SmsService) CglibProxyFactory.getProxy(SmsService.class);
        proxy.send("test cglib msg");
    }
}
