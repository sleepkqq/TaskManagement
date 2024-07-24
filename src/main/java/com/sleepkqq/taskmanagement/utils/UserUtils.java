package com.sleepkqq.taskmanagement.utils;

import com.sleepkqq.taskmanagement.dto.responses.UserResponse;
import com.sleepkqq.taskmanagement.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserUtils {

    public static List<UserResponse> convertUsersToResponse(List<User> tasks) {
        return tasks.stream().map(UserResponse::fromUser).toList();
    }

}
