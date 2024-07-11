package com.sleepkqq.taskmanagement.service;

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

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(String title, String description, String assignee, String reporter) {
        var timeNow = LocalDateTime.now();
        var assigneeUser = userService.loadUserByUsername(assignee);
        var reporterUser = userService.loadUserByUsername(reporter);

        var task = Task.builder()
                .title(title)
                .description(description)
                .status(TaskStatus.TO_DO)
                .priority(TaskPriority.MEDIUM)
                .createdDate(timeNow)
                .updatedDate(timeNow)
                .assignee(assigneeUser)
                .reporter(reporterUser)
                .build();

        reporterUser.getReportedTasks().add(task);
        assigneeUser.getAssignedTasks().add(task);
        userService.save(reporterUser);
        userService.save(assigneeUser);

        return taskRepository.save(task);
    }

}
