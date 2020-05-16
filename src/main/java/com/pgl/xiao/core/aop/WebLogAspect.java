package com.pgl.xiao.core.aop;
import com.pgl.xiao.core.Constants;
import com.pgl.xiao.utils.BasicUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Aspect
//  Spring MVC的控制器日志切面
public class WebLogAspect {

    /**
     * 设置日志切面-SpringMVC的类与方法
     */
    @Pointcut("execution(* com.pgl.xiao.controller.*.*(..))")
    public void webMethod() {
    }

    /**
     * 当请求处理之前,日志记录访问者的IP地址,请求参数的值
     * @param joinPoint 当前即将处理的Spring MVC的类对象
     */
    @Before("webMethod()")
    public void handleBefore(JoinPoint joinPoint) {
        Logger logger = LogManager.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        String tag = BasicUtils.getLogTag(joinPoint);
        // 得到Web的请求的HttpServletRequest对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // 日志打印访问者的IP地址
        logger.debug("访问者地址:" + Constants.CHAR_SEPARATOR + getIpAddress(request));
        // 日志打出访问者的请求参数
        logger.debug("请求参数:"+ Constants.CHAR_SEPARATOR);
        // 得到参数键值对
        Map<String,String[]> keyVals = request.getParameterMap();
        logger.debug("请求参数:" + Constants.CHAR_SEPARATOR);
//        //遍历参数
//        for(Iterator iter = keyVals.entrySet().iterator(); iter.hasNext();) {
//            Map.Entry element = (Map.Entry) iter.next();
//            //key值
//            Object strKey = element.getKey();
//            //value,数组形式
//            String[] value = (String[]) element.getValue();
//            logger.debug("key:" + Constants.CHAR_SEPARATOR + strKey.toString());
//            for(int i = 0; i < value.length; i++){
//                logger.debug( "value:  " + Constants.CHAR_SEPARATOR + value[i]);
//            }
//        }
    }

    /**
     * 日志环绕处理,当SpringMVC处理请求时,记录处理的耗时时间
     * @param proceedingJoinPoint   被代理的Spring MVC对象
     * @return  被增加的Spring MVC对象
     * @throws Throwable    可能发生的异常
     */
    @Around("webMethod()")
    public Object arroundHandle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Logger logger = LogManager.getLogger(proceedingJoinPoint.getSignature().getDeclaringTypeName());
        String tag = BasicUtils.getLogTag(proceedingJoinPoint);
        long startTime = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        // 计算出耗时-单位秒
        double spendTime = endTime / 1000.0;
        logger.debug("执行耗时" + Constants.CHAR_SEPARATOR + spendTime + "秒");
        return obj;
    }

    /**
     * 日志打印SpirngMVC处理之后的返回值
     */
    @AfterReturning(value = "webMethod()", returning = "obj")
    public void afterReturn(JoinPoint joinPoint, Object obj) {
        Logger logger = LogManager.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        logger.debug("正常处理的返回值" + Constants.CHAR_SEPARATOR + obj);
    }

    /**
     * 当SpringMVC处理请求时,出现异常时,记录当前的异常信息
     * @param joinPoint 当前处理的代理对象
     * @param e 异常
     */
    @AfterThrowing(value = "webMethod()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        Logger logger = LogManager.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        String tag = BasicUtils.getLogTag(joinPoint);
        logger.error("异常通知：" + Constants.CHAR_SEPARATOR + e.getMessage());
    }

    /**
     * SpringMVC的最终处理返回结果
     * @param joinPoint 被代理的对象
     */
    @After(value = "webMethod()")
    public void after(JoinPoint joinPoint) {
        Logger logger = LogManager.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        logger.debug("最终的返回值：" + joinPoint.getArgs());
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
