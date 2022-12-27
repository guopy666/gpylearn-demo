package com.gpy.invocation.cglibinvocation;

/**
 * @ClassName SmsService
 * @Description
 * @Author guopy
 * @Date 2022/2/17 16:18
 */
public class SmsService {
    public String send(String msg){
        System.out.println("cglib send msg : " + msg);
        return msg;
    }
}
