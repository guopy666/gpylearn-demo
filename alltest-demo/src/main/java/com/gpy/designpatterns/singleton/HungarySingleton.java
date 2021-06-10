package com.gpy.designpatterns.singleton;

/**
 * @ClassName HungarySingleton 饿汉式单例模式
 * @Description
 * @Author guopy
 * @Date 2021/6/7 10:19
 */
public class HungarySingleton {
    private static final HungarySingleton INSTANCE = new HungarySingleton();
    private HungarySingleton(){}

    public HungarySingleton getInstance(){
        return INSTANCE;
    }
}
