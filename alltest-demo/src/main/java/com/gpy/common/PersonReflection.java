package com.gpy.common;

import java.lang.reflect.Constructor;

/**
 * @ClassName PersonReflection
 * @Description
 * @Author guopy
 * @Date 2026/4/14 15:47
 */
public class PersonReflection {
    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("com.gpy.common.Content");
        Constructor<?> constructor = aClass.getConstructor();
        Content object = (Content)constructor.newInstance();
        object.setImg("1");
        System.out.println(object);
    }
}
