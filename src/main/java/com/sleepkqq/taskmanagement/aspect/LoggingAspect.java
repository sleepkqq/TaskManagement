package com.sleepkqq.taskmanagement.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("com.sleepkqq.taskmanagement.aspect.Pointcuts.allControllersMethods()")
    public ResponseEntity<?> servicesMethodsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var methodName = proceedingJoinPoint.getSignature().getName();
        var className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        var username = authentication.getName();

        log.info("Method '{}' in controller '{}' is called by user '{}'", methodName, className, username);

        var start = System.currentTimeMillis();
        ResponseEntity<?> responseEntity;

        try {
            responseEntity = (ResponseEntity<?>) proceedingJoinPoint.proceed();
        } catch (Exception e) {
            log.error("Method '{}' in controller '{}' that was called by user '{}', failed: {}",
                    methodName, className, username, e.toString());
            throw e;
        }

        var end = System.currentTimeMillis();

        log.info("Method '{}' in controller '{}' that was called by user '{}', has ended successfully in {} millis",
                methodName, className, username, end - start);

        return responseEntity;
    }

}
