package com.sleepkqq.taskmanagement.service;

import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.model.Task;
import com.sleepkqq.taskmanagement.model.enums.TaskPriority;
import com.sleepkqq.taskmanagement.model.enums.TaskStatus;
import com.sleepkqq.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<TaskResponse> getAllTasks() {
        return convertTasks(taskRepository.findAll());
    }

    public List<TaskResponse> getUserAssignedTasks(String username) {
        return convertTasks(userService.loadUserByUsername(username).getAssignedTasks());
    }

    public List<TaskResponse> getUserReportedTasks(String username) {
        return convertTasks(userService.loadUserByUsername(username).getReportedTasks());
    }

    public Task createTask(String title, String description, String assignee, String reporter) {
        var timeNow = LocalDateTime.now();
        var task = Task.builder()
                .title(title)
                .description(description)
                .status(TaskStatus.TO_DO)
                .priority(TaskPriority.MEDIUM)
                .createdDate(timeNow)
                .updatedDate(timeNow)
                .assignee(userService.loadUserByUsername(assignee))
                .reporter(userService.loadUserByUsername(reporter))
                .build();

        return taskRepository.save(task);
    }

    private List<TaskResponse> convertTasks(List<Task> tasks) {
        return tasks.stream().map(TaskResponse::fromTask).toList();
    }

}
