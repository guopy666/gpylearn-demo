package com.gpy.designpatterns.chainofresponsebility.basedemo;


/**
 * @ClassName Chain
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:04
 */
public abstract class Handler {

    protected Handler successor = null;

    public void setSuccessor(Handler handler){
        this.successor = handler;
    }

    public abstract void handle();
}
