package com.gpy.designpatterns.singleton;

/**
 * @ClassName InnerClassSingleton 静态内部类实现的单例模式
 * @Description
 * @Author guopy
 * @Date 2021/6/9 17:31
 */
public class InnerClassSingleton {

    private static class SingletonHolder{
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }

    public static InnerClassSingleton getInstance(){
        return SingletonHolder.INSTANCE;
    }

}
