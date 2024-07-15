package com.sleepkqq.taskmanagement.utils;

import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.model.Task;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TaskUtils {

    public static List<TaskResponse> convertTasks(List<Task> tasks) {
        return tasks.stream().map(TaskResponse::fromTask).toList();
    }

}
