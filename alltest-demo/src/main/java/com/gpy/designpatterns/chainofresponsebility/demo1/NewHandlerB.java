package com.gpy.designpatterns.chainofresponsebility.demo1;

/**
 * @ClassName NewHandlerB
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:32
 */
public class NewHandlerB extends NewHandler{

    @Override
    public boolean doHandle() {
        System.out.println("into NewHandlerB handle request");
        return true;
    }
}
