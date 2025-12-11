package com.example.demo.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.demo.controller..*(..))")
    public Object logAroundControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className  = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        String fullMethod = className + "." + methodName;

        // 安全打印参数（避免出现 request/response 这种巨大对象）
        String argsString = Arrays.stream(joinPoint.getArgs())
                .map(this::safeToString)
                .collect(Collectors.joining(", "));

        logger.info("➡️ Entering {} with args: {}", fullMethod, argsString);

        try {
            Object result = joinPoint.proceed();

            long elapsed = System.currentTimeMillis() - startTime;
            logger.info("✅ Exiting {} took {} ms", fullMethod, elapsed);

            return result;

        } catch (Throwable ex) {
            long elapsed = System.currentTimeMillis() - startTime;
            logger.error("❌ Exception in {} after {} ms: {}",
                    fullMethod, elapsed, ex.getMessage(), ex);
            throw ex;
        }
    }

    private String safeToString(Object arg) {
        if (arg == null) return "null";
        if (arg instanceof HttpServletRequest) return "HttpServletRequest";
        if (arg instanceof HttpServletResponse) return "HttpServletResponse";
        return arg.toString();
    }
}
