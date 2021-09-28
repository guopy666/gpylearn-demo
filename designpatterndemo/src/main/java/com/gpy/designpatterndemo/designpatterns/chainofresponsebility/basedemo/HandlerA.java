package com.gpy.designpatterndemo.designpatterns.chainofresponsebility.basedemo;

/**
 * @ClassName HandlerA
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:06
 */
public class HandlerA extends Handler {
    @Override
    public void handle() {
        System.out.println("into A handler , handle this request begin");
        boolean handled = false;
        //... 如果一系列处理完后，请求已经不需要后续处理，handled 置为 true
        handled = true;
        if (!handled && successor != null) {
            successor.handle();
        }
    }
}
