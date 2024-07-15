package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.dto.responses.UserResponse;
import com.sleepkqq.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        var user = userService.loadUserByUsername(username);
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @PostMapping("/{username}/add-admin")
    public ResponseEntity<UserResponse> addAdminRole(@PathVariable String username) {
        var user = userService.addAdminRole(username);
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @PostMapping("/{username}/remove-admin")
    public ResponseEntity<UserResponse> removeAdminRole(@PathVariable String username) {
        var user = userService.removeAdminRole(username);
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @GetMapping("/read-assigned/{username}")
    public List<TaskResponse> getUserAssignedTasks(@PathVariable String username) {
        return userService.getUserAssignedTasks(username);
    }

    @GetMapping("/read-reported/{username}")
    public List<TaskResponse> getUserReportedTasks(@PathVariable String username) {
        return userService.getUserReportedTasks(username);
    }

}