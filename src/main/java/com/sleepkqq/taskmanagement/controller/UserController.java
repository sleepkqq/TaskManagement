package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.responses.UserResponse;
import com.sleepkqq.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        try {
            var user = userService.loadUserByUsername(username);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{username}/add-admin")
    public ResponseEntity<UserResponse> addAdminRole(@PathVariable String username) {
        try {
            var user = userService.addAdminRole(username);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{username}/remove-admin")
    public ResponseEntity<UserResponse> removeAdminRole(@PathVariable String username) {
        try {
            var user = userService.removeAdminRole(username);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}