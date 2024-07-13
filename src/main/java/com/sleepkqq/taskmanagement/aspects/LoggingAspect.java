package com.sleepkqq.taskmanagement.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.sleepkqq.taskmanagement.service..*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = "anonymous";
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        }
        var methodName = joinPoint.getSignature().getName();
        var className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Method '{}' in class '{}' is called by user '{}'", methodName, className, username);
    }

}
