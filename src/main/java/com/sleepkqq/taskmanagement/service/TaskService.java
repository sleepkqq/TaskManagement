package com.sleepkqq.taskmanagement.service;

import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.model.Task;
import com.sleepkqq.taskmanagement.model.enums.TaskPriority;
import com.sleepkqq.taskmanagement.model.enums.TaskStatus;
import com.sleepkqq.taskmanagement.repository.TaskRepository;
import com.sleepkqq.taskmanagement.utils.TaskUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public Task createTask(String title, String description, TaskStatus status, TaskPriority priority,
                           LocalDateTime dueTime, String assignee, String reporter) {
        var timeNow = LocalDateTime.now();
        var task = Task.builder()
                .title(title)
                .description(description)
                .status(status)
                .priority(priority)
                .createdDate(timeNow)
                .updatedDate(timeNow)
                .dueDate(dueTime)
                .assignee(userService.loadUserByUsername(assignee))
                .reporter(userService.loadUserByUsername(reporter))
                .build();

        return taskRepository.save(task);
    }

    public TaskResponse getTask(long id) {
        return TaskResponse.fromTask(taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task '" + id + "' not found")));
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskResponse> getAllTasks() {
        return TaskUtils.convertTasks(taskRepository.findAll());
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        return TaskUtils.convertTasks(taskRepository.findByStatus(status));
    }

    public List<TaskResponse> getTasksByPriority(TaskPriority priority) {
        return TaskUtils.convertTasks(taskRepository.findByPriority(priority));
    }

}
