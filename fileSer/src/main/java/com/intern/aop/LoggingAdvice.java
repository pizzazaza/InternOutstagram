package com.intern.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAdvice {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

    @Before("execution(* com.intern.service.*.*(..)) || " +
            "execution(* com.intern.controller.*.*(..)) || " +
            "execution(* com.intern.dao.*.*(..)) || "+
            "execution(* com.intern.common.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("\n=============" + joinPoint.getTarget().getClass()+ " : "+
                joinPoint.getSignature().getName() + "() is running =============");
    }
    //등록된 패키지에 함수가 실행할 때 log를 출력

    @AfterReturning("execution(* com.intern.service.*.*(..)) || " +
            "execution(* com.intern.controller.*.*(..)) || " +
            "execution(* com.intern.dao.*.*(..)) || " +
            "execution(* com.intern.common.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("\n=============" + joinPoint.getTarget().getClass()+ " : " +
                joinPoint.getSignature().getName() + "() is over =============");
    }
    //등록된 패키지에 함수가 종할할 때 log를 출력

    @AfterThrowing(pointcut = "execution(* com.intern.service.*.*(..)) || " +
            "execution(* com.intern.controller.*.*(..)) || " +
            "execution(* com.intern.dao.*.*(..)) || " + 
            "execution(* com.intern.common.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.error("\n=============" + joinPoint.getTarget().getClass()+ " : " +
                joinPoint.getSignature().getName() + "() is something wrong =============");
        logger.error("\n=============error message : " + exception.getCause().getMessage() + "=============");
        exception.printStackTrace();
    }
    //등록된 패키지에 함수에서 Exception이 발생할 때 log를 출력
}