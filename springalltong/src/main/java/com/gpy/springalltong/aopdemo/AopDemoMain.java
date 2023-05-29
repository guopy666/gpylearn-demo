package com.gpy.springalltong.aopdemo;

import com.gpy.springalltong.aopdemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName AopDemoMain
 * @Description
 * @Author guopy
 * @Date 2023/3/15 10:59
 */
@SpringBootApplication
public class AopDemoMain implements ApplicationRunner {

    @Autowired
    private TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(AopDemoMain.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String spring = testService.testHello("spring");
        System.out.println(spring);
    }
}
