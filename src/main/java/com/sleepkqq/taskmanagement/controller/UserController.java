package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.dto.responses.UserResponse;
import com.sleepkqq.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get user by username")
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        var user = userService.loadUserByUsername(username);
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @Operation(summary = "Get all users")
    @GetMapping("/all/read")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var tasks = userService.getAllUsers();
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Delete user by username")
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add admin role to user")
    @PostMapping("/{username}/add-admin")
    public ResponseEntity<UserResponse> addAdminRole(@PathVariable String username) {
        var user = userService.addAdminRole(username);
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @Operation(summary = "Remove admin role from user")
    @PostMapping("/{username}/remove-admin")
    public ResponseEntity<UserResponse> removeAdminRole(@PathVariable String username) {
        var user = userService.removeAdminRole(username);
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @Operation(summary = "Get assigned tasks for user")
    @GetMapping("/{username}/read-assigned-tasks")
    public ResponseEntity<List<TaskResponse>> getUserAssignedTasks(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserAssignedTasks(username));
    }

    @Operation(summary = "Get reported tasks for user")
    @GetMapping("/{username}/read-reported-tasks")
    public ResponseEntity<List<TaskResponse>> getUserReportedTasks(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserReportedTasks(username));
    }

}