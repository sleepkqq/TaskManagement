package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.TaskCreateRequest;
import com.sleepkqq.taskmanagement.dto.TaskResponse;
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

    @GetMapping("/read-all")
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/read-assigned/{username}")
    public List<TaskResponse> getUserAssignedTasks(@PathVariable String username) {
        return taskService.getUserAssignedTasks(username);
    }

    @GetMapping("/read-reported/{username}")
    public List<TaskResponse> getUserReportedTasks(@PathVariable String username) {
        return taskService.getUserReportedTasks(username);
    }

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

}
