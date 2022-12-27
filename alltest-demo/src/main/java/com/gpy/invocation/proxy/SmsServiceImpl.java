package com.gpy.invocation.proxy;

/**
 * @ClassName SmsServiceImpl
 * @Description
 * @Author guopy
 * @Date 2022/2/17 16:18
 */
public class SmsServiceImpl implements SmsService {
    @Override
    public String send(String msg) {
        System.out.println("SmsService:" + msg);
        return msg;
    }
}
