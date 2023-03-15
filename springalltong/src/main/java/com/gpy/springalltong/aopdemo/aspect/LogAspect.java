package com.gpy.springalltong.aopdemo.aspect;

import com.alibaba.fastjson.JSONObject;
import com.gpy.springalltong.aopdemo.annotation.ServiceLog;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @ClassName LogAspect
 * @Description
 * @Author guopy
 * @Date 2023/3/15 10:43
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    // 本地线程
    ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * @Description  定义拦截入口，service层使用
     * @Author guopy
     * @Date 2023/3/15 10:44
     * @param
     * @Return void
     **/
    @Pointcut("@annotation(com.gpy.springalltong.aopdemo.annotation.ServiceLog)")
    private void serviceLogAspect(){}

    @Around("serviceLogAspect()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        threadLocal.set(System.currentTimeMillis());
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?> returnType = method.getReturnType();
        ServiceLog annotation = method.getAnnotation(ServiceLog.class);
        String operationRemark = annotation.operationRemark();
        String name = annotation.name();

        log.info("=======================================================");
        //方法执行前
        log.info("-----------请求内容---------");

        log.info("请求类方法: {}", signature);
        log.info("请求方法参数: {}", Arrays.toString(joinPoint.getArgs()));
        log.info("请求注解参数name: {}  请求注解参数operationRemark: {}", name, operationRemark);
        log.info("请求方法返回类型：{}", returnType.getTypeName());

        // 执行目标方法
        Object proceed = joinPoint.proceed();

        // 方法执行后
        log.info("-----------响应内容---------");
        log.info("response内容 {}", JSONObject.toJSON(proceed));
        log.info("请求处理时间: {}", (System.currentTimeMillis() - threadLocal.get()));
        return proceed;
    }
}
