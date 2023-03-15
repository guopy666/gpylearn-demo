package com.gpy.springalltong.aopdemo.service;

import com.gpy.springalltong.aopdemo.annotation.ServiceLog;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestService
 * @Description
 * @Author guopy
 * @Date 2023/3/15 10:42
 */
@Service
public class TestService {
    @ServiceLog(name = "zheshi hello name", operationRemark = "测试aspect")
    public String testHello(String name){
        return "hello ," + name;
    }
}
