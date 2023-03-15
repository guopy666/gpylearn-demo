package com.gpy.springalltong.aopdemo.annotation;

import java.lang.annotation.*;

/**
 * @Description 自定义 serviceLog 注解，在service的方法使用
 * @Author guopy
 * @Date 2023/3/15 10:40
 * @Return
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLog {
    String name() default "";
    String operationRemark() default "";
}
