package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.TaskCreateRequest;
import com.sleepkqq.taskmanagement.dto.TaskCreateResponse;
import com.sleepkqq.taskmanagement.model.Task;
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
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/create")
    public ResponseEntity<TaskCreateResponse> createTask(@RequestBody TaskCreateRequest request) {
        var createdTask = taskService.createTask(
                request.title(),
                request.description(),
                request.assignee(),
                request.reporter()
        );
        return ResponseEntity.status(201).body(TaskCreateResponse.fromTask(createdTask));
    }

}
