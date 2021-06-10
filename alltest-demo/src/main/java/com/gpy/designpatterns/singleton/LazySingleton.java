package com.gpy.designpatterns.singleton;

/**
 * @ClassName LazySingleton 懒汉式单例
 * @Description
 * @Author guopy
 * @Date 2021/6/9 17:17
 */
public class LazySingleton {
    public static LazySingleton INSTANCE = null;

    private LazySingleton(){}

    public synchronized LazySingleton getInstance(){
        if (INSTANCE == null){
            return new LazySingleton();
        }
        return INSTANCE;
    }

}
