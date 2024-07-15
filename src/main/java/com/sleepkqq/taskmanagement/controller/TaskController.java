package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.requests.TaskCreateRequest;
import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.model.enums.TaskPriority;
import com.sleepkqq.taskmanagement.model.enums.TaskStatus;
import com.sleepkqq.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request) {
        var createdTask = taskService.createTask(
                request.title(),
                request.description(),
                request.assignee(),
                request.reporter()
        );
        return ResponseEntity.status(201).body(TaskResponse.fromTask(createdTask));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable long id) {
        var task = taskService.getTask(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        var tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/read-by-status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable TaskStatus status) {
        var tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/read-by-priority/{priority}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable TaskPriority priority) {
        var tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

}
