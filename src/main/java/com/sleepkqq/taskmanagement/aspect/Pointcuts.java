package com.sleepkqq.taskmanagement.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.sleepkqq.taskmanagement.service..*(..))")
    private void allMethodsFromServices() {}

    @Pointcut("execution(* com.sleepkqq.taskmanagement.service.AuthenticationService..*(..))")
    private void allMethodsFromAuthenticationService() {}

    @Pointcut("execution(* com.sleepkqq.taskmanagement.service.JwtService..*(..))")
    private void allMethodsFromJwtService() {}

    @Pointcut("allMethodsFromServices() && !allMethodsFromAuthenticationService() && !allMethodsFromJwtService()")
    public void allServicesNotIncludingAuth() {}

}
