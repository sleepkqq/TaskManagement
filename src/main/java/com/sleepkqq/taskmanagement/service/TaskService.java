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

    public Task getTask(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task '" + id + "' not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }

}
