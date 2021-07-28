package com.gpy.designpatterns.chainofresponsebility.demo2;

/**
 * @ClassName HandlerImplB
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:49
 */
public class HandlerImplB implements IHandler{
    @Override
    public boolean handle() {
        System.out.println("into HandlerImplB, request is handled");
        return true;
    }
}
