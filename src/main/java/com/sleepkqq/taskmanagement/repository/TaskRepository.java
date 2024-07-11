package com.sleepkqq.taskmanagement.repository;

import com.sleepkqq.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}