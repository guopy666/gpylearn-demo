package com.gpy.designpatterns.chainofresponsebility.demo1;

/**
 * @ClassName NewHandlerChain
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:33
 */
public class NewHandlerChain {

    private NewHandler head = null;
    private NewHandler tail = null;

    public void addNewHandler(NewHandler newHandler){
        newHandler.setSuccessor(null);
        if (head == null ){
            this.head = newHandler;
            this.tail = newHandler;
            return;
        }
        tail.setSuccessor(newHandler);
    }

    public void handle(){
        if (null != head){
            head.handle();
        }
    }

}
