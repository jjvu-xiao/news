package com.pgl.xiao.utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

@Component
@Aspect
//  Spring MVC的控制器日志切面
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.pgl.xiao.controller.*.*(..))")
    public void webMethod() {
    }

    @Before("webMethod()")
    public void handleBefore(JoinPoint joinPoint) {
        String tag = BasicUtils.getLogTag(joinPoint);
        // 得到Web的请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        this.logger.info(tag + "访问者地址:" + Constants.CHAR_SEPARATOR + getIpAddress(request));
        logger.info("请求参数:"+ Constants.CHAR_SEPARATOR);
        // 得到参数键值对
        Map<String,String[]> keyVals = request.getParameterMap();
        //遍历
        for(Iterator iter = keyVals.entrySet().iterator(); iter.hasNext();) {
            Map.Entry element = (Map.Entry) iter.next();
            //key值
            Object strKey = element.getKey();
            //value,数组形式
            String[] value = (String[]) element.getValue();
            logger.info(tag + "key:" + Constants.CHAR_SEPARATOR + strKey.toString());
            for(int i = 0; i < value.length; i++){
                logger.info(tag + "value:  " + Constants.CHAR_SEPARATOR + value[i]);
            }
        }
    }

    @Around("webMethod()")
    public Object arroundHandle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String tag = BasicUtils.getLogTag(proceedingJoinPoint);
        long startTime = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        double spendTime = endTime / 1000.0;
        logger.info(tag + "执行耗时" + Constants.CHAR_SEPARATOR + spendTime + "秒");
        return obj;
    }

    @AfterReturning("webMethod()")
    public void afterReturn(JoinPoint joinPoint) {
    }


    @AfterThrowing(value = "webMethod()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        String tag = BasicUtils.getLogTag(joinPoint);
        logger.info(tag + "异常通知" + Constants.CHAR_SEPARATOR + e.getMessage());
    }

    @After("webMethod()")
    public void after() {
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }


}
