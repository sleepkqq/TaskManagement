package com.sleepkqq.taskmanagement.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/secured")
public class MainController {

    @GetMapping("/user")
    public String userAccess() {
        return SecurityContextHolder.getContext().getAuthentication().getName() + " " + SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

}
