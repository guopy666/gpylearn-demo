package com.gpy.designpatterns.chainofresponsibility.basedemo;

/**
 * @ClassName HandlerB
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:11
 */
public class HandlerB extends Handler {
    @Override
    public void handle() {
        System.out.println("into B hander, this request is handled begin");
        boolean handled = false;
        //...
        if (!handled && successor != null) {
            successor.handle();
        }
    }
}
