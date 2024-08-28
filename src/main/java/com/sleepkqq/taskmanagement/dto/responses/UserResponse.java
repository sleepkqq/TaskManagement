package com.sleepkqq.taskmanagement.dto.responses;

import com.sleepkqq.taskmanagement.model.Task;
import com.sleepkqq.taskmanagement.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "User Response")
public record UserResponse(
        @Schema(description = "User ID", example = "1")
        long id,
        @Schema(description = "Username", example = "johndoe")
        String username,
        @Schema(description = "Email address", example = "johndoe@example.com")
        String email,
        @Schema(description = "List of user roles", example = "[\"ADMIN\", \"USER\"]")
        List<String> roles,
        @Schema(description = "List of assigned task IDs", example = "[1, 2, 3]")
        List<Long> assignedTasks,
        @Schema(description = "List of reported task IDs", example = "[4, 5, 6]")
        List<Long> reportedTasks,
        @Schema(description = "Set of friends' usernames", example = "[user1, user2, user3]")
        Set<String> friendsUsernames) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Enum::name).toList(),
                user.getAssignedTasks().stream().map(Task::getId).toList(),
                user.getReportedTasks().stream().map(Task::getId).toList(),
                user.getFriends().stream().map(User::getUsername).collect(Collectors.toSet())
        );
    }

}
