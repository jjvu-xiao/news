package com.pgl.xiao.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 基础日志
 */
public class BasicLogger {

    Logger logger = LogManager.getLogger(getClass());

    public void before(JoinPoint joinPoint) {
//        logger.info("前置通知:模拟执行权限....");
//        logger.info("目标类是:" + joinPoint.getTarget());
//        logger.info(",被织入增强处理的目标方法为:" + joinPoint.getSignature().getName());
    }

    public void afterReturn(JoinPoint joinPoint) {
//        logger.info("后置通知:模拟记录日志....");
//        logger.info(",被织入增强处理的目标方法为:" + joinPoint.getSignature().getName());
    }

    public Object arround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        // 开始
//        logger.info("环绕开始:执行目标方法执行之前，开启事务...");
//        // 执行当前目标方法
        Object obj = proceedingJoinPoint.proceed();
//        // 结束
//        logger.info("环绕结束：执行目标方法之后，模拟关闭事务");
        return obj;
    }

    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
//        logger.info("异常通知" + "出错了" + e.getMessage());
    }

    public void after() {
//        logger.info("最终通知:模拟方法结束后释放资源...");
    }
}
