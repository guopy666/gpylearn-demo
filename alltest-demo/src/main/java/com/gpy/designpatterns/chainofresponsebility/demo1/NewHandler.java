package com.gpy.designpatterns.chainofresponsebility.demo1;

/**
 * @ClassName NewHandler
 * 相比于第一版 Handler ，把successor.handler()方法抽出来，使具体的实现类只关注自己的业务
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:28
 */
public abstract class NewHandler {
    protected NewHandler successor = null;

    public void setSuccessor(NewHandler handler){
        this.successor = handler;
    }

    public final void handle(){
        boolean b = doHandle();
        if (!b && successor != null){
            successor.handle();
        }

    }

    public abstract boolean doHandle();

}
