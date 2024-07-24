package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.requests.TaskCreateRequest;
import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.model.enums.TaskPriority;
import com.sleepkqq.taskmanagement.model.enums.TaskStatus;
import com.sleepkqq.taskmanagement.service.TaskService;
import com.sleepkqq.taskmanagement.utils.TaskUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a new task")
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskCreateRequest request) {
        var createdTask = taskService.createTask(
                request.title(),
                request.description(),
                request.status(),
                request.priority(),
                request.dueDate(),
                request.assignee(),
                request.reporter()
        );
        return ResponseEntity.status(CREATED).body(TaskResponse.fromTask(createdTask));
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable long id) {
        return ResponseEntity.ok(TaskResponse.fromTask(taskService.getTask(id)));
    }

    @Operation(summary = "Get all tasks")
    @GetMapping("/get/all")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(TaskUtils.convertTasksToResponse(taskService.getAllTasks()));
    }

    @Operation(summary = "Delete task by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get tasks by status")
    @GetMapping("/read-by-status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable TaskStatus status) {
        return ResponseEntity.ok(TaskUtils.convertTasksToResponse(taskService.getTasksByStatus(status)));
    }

    @Operation(summary = "Get tasks by priority")
    @GetMapping("/read-by-priority/{priority}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable TaskPriority priority) {
        return ResponseEntity.ok(TaskUtils.convertTasksToResponse(taskService.getTasksByPriority(priority)));
    }

}
