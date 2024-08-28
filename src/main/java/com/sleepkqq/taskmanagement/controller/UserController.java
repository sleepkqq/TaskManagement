package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.dto.responses.UserResponse;
import com.sleepkqq.taskmanagement.service.UserService;
import com.sleepkqq.taskmanagement.utils.TaskUtils;
import com.sleepkqq.taskmanagement.utils.UserUtils;
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
        return ResponseEntity.ok(UserResponse.fromUser(userService.loadUserByUsername(username)));
    }

    @Operation(summary = "Get all users")
    @GetMapping("/get/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(UserUtils.convertUsersToResponse(userService.getAllUsers()));
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
        return ResponseEntity.ok(UserResponse.fromUser(userService.addAdminRole(username)));
    }

    @Operation(summary = "Remove admin role from user")
    @PostMapping("/{username}/remove-admin")
    public ResponseEntity<UserResponse> removeAdminRole(@PathVariable String username) {
        return ResponseEntity.ok(UserResponse.fromUser(userService.removeAdminRole(username)));
    }

    @Operation(summary = "Get assigned tasks for user")
    @GetMapping("/{username}/read-assigned-tasks")
    public ResponseEntity<List<TaskResponse>> getUserAssignedTasks(@PathVariable String username) {
        return ResponseEntity.ok(TaskUtils.convertTasksToResponse(userService.getUserAssignedTasks(username)));
    }

    @Operation(summary = "Get reported tasks for user")
    @GetMapping("/{username}/read-reported-tasks")
    public ResponseEntity<List<TaskResponse>> getUserReportedTasks(@PathVariable String username) {
        return ResponseEntity.ok(TaskUtils.convertTasksToResponse(userService.getUserReportedTasks(username)));
    }

    @Operation(summary = "Add friendship between two users")
    @PostMapping("/add-friendship")
    public ResponseEntity<Void> addFriendship(@RequestParam String username1, @RequestParam String username2) {
        userService.addFriendship(username1, username2);
        return ResponseEntity.ok().build();
    }

}