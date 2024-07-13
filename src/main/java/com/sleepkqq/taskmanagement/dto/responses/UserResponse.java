package com.sleepkqq.taskmanagement.dto.responses;

import com.sleepkqq.taskmanagement.model.Task;
import com.sleepkqq.taskmanagement.model.User;

import java.util.List;

public record UserResponse(long id, String username, String email, List<String> roles, List<Long> assignedTasks, List<Long> reportedTasks) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Enum::name).toList(),
                user.getAssignedTasks().stream().map(Task::getId).toList(),
                user.getReportedTasks().stream().map(Task::getId).toList()
        );
    }

}
