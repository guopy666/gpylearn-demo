package com.gpy.designpatterns.chainofresponsibility.basedemo;

/**
 * @ClassName HandlerChain
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:11
 */
public class HandlerChain {

    private Handler head = null;
    private Handler tail = null;

    public void addHandler(Handler handler){

        handler.setSuccessor(null);

        if (null == head){
            this.head = handler;
            this.tail = handler;
            return;
        }

        tail.setSuccessor(handler);
        tail = handler;
    }

    public void handle(){
        if (head != null){
            head.handle();
        }
    }
}
