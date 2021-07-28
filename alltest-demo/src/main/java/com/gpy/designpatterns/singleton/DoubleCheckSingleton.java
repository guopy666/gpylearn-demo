package com.gpy.designpatterns.singleton;

/**
 * @ClassName DoubleCheckSingleton 双重检测锁机制单例模式
 * @Description
 * @Author guopy
 * @Date 2021/6/9 17:20
 */
public class DoubleCheckSingleton {
    public static DoubleCheckSingleton INSTANCE = null;

    private DoubleCheckSingleton(){

    }

    public static DoubleCheckSingleton getInstance(){
        if (null == INSTANCE){
            synchronized (DoubleCheckSingleton.class){
                //在此处的判断是如果多个线程同时请求第一个if条件判断时，可能都会进来创建多个实例
                if (null == INSTANCE){
                    return new DoubleCheckSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
