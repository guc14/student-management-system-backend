package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 这个切点意思是：
     * 拦截 com.example.demo.controller 包及其子包下的所有 public 方法
     */
    @Around("execution(* com.example.demo.controller..*(..))")
    public Object logAroundControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        long startTime = System.currentTimeMillis();

        logger.info("➡️ Entering {} with args = {}", methodName, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();  // 真正执行 Controller 方法

            long elapsed = System.currentTimeMillis() - startTime;
            logger.info("✅ Exiting {} took {} ms", methodName, elapsed);

            return result;
        } catch (Throwable ex) {
            long elapsed = System.currentTimeMillis() - startTime;
            logger.error("❌ Exception in {} after {} ms", methodName, elapsed, ex);
            throw ex; // 继续往外抛，让全局异常处理去处理
        }
    }
}
