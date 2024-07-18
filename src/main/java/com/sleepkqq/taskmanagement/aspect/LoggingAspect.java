package com.sleepkqq.taskmanagement.aspect;

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

    @Before("com.sleepkqq.taskmanagement.aspect.Pointcuts.allServicesNotIncludingAuth()")
    public void servicesMethodsAdvice(JoinPoint joinPoint) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            var methodName = joinPoint.getSignature().getName();
            var className = joinPoint.getTarget().getClass().getSimpleName();
            var username = authentication.getName();

            log.info("Method '{}' in class '{}' is called by user '{}'", methodName, className, username);
        }
    }

}
