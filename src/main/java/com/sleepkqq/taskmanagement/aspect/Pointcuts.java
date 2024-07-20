package com.sleepkqq.taskmanagement.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.sleepkqq.taskmanagement.controller..*(..))")
    public void allControllersMethods() {}

}
