package com.gpy.invocation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName TestTargetObjectInvocation
 * @Description
 * @Author guopy
 * @Date 2022/2/16 15:23
 */
public class TestTargetObjectInvocation {

    public static void main(String[] args) {
        try {
            // 获取 TargetObject 类的 Class 对象
            Class<?> targetClass = Class.forName("com.gpy.invocation.TargetObject");
            // 创建 TargetObject 类的实例
            TargetObject object = (TargetObject)targetClass.newInstance();

            // 获取该类中的所有方法名称
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(method);
            }

            // 获取指定方法并使用
            Method publicMethod = targetClass.getDeclaredMethod("publicMethod", String.class);
            publicMethod.invoke(object, "公共方法参数");

            // 获取指定属性并对参数进行修改
            Field value = targetClass.getDeclaredField("value");

            value.setAccessible(true);// 为了修改属性取消安全检查
            value.set(object, "Guopy");

            // 调用私有方法
            Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
            privateMethod.setAccessible(true);// 为了调用私有方法，取消安全检查
            privateMethod.invoke(object);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }



}
