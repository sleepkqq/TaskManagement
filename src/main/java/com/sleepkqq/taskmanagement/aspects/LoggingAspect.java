package com.sleepkqq.taskmanagement.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.sleepkqq.taskmanagement.service..*(..))")
    public void servicesMethodsAdvice(JoinPoint joinPoint) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            var methodName = joinPoint.getSignature().getName();
            var className = joinPoint.getTarget().getClass().getSimpleName();

            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                var username = authentication.getName();
                log.info("Method '{}' in class '{}' is called by user '{}'", methodName, className, username);
            }
        }
    }

}
