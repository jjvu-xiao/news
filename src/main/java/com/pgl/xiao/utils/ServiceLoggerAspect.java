package com.pgl.xiao.utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础日志
 */
public class ServiceLoggerAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void before(JoinPoint joinPoint) {
    }

    public void afterReturn(JoinPoint joinPoint) {
    }

    public Object arround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object obj = proceedingJoinPoint.proceed();
        return obj;
    }

    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
    }

    public void after() {
    }
}
