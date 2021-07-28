package com.gpy.designpatterns.chainofresponsibility.demo2;

/**
 * @ClassName HandlerImplA
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:49
 */
public class HandlerImplA implements IHandler{
    @Override
    public boolean handle() {

        System.out.println("into HandlerImplA ,request is not handled");

        return false;
    }
}
