package com.gpy.designpatterns.chainofresponsebility.demo1;

/**
 * @ClassName NewHandlerA
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:31
 */
public class NewHandlerA extends NewHandler{
    @Override
    public boolean doHandle() {

        System.out.println("into NewHandlerA handle request");
        return false;
    }
}
