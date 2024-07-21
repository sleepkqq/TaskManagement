package com.sleepkqq.taskmanagement.service;

import com.sleepkqq.taskmanagement.dto.responses.TaskResponse;
import com.sleepkqq.taskmanagement.dto.responses.UserResponse;
import com.sleepkqq.taskmanagement.model.User;
import com.sleepkqq.taskmanagement.model.enums.Role;
import com.sleepkqq.taskmanagement.repository.UserRepository;
import com.sleepkqq.taskmanagement.utils.TaskUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponse::fromUser).toList();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        var username = user.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User with username '" + username + "' already exists");
        }

        var email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email '" + email + "' already exists");
        }

        return save(user);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public User addAdminRole(String username) {
        var user = loadUserByUsername(username);
        if (!user.getRoles().contains(Role.ADMIN)) {
            user.getRoles().add(Role.ADMIN);
            save(user);
        }
        return user;
    }

    public User removeAdminRole(String username) {
        var user = loadUserByUsername(username);
        user.getRoles().removeIf(role -> role.equals(Role.ADMIN));
        if (user.getRoles().isEmpty()) {
            user.getRoles().add(Role.USER);
        }
        save(user);
        return user;
    }

    public List<TaskResponse> getUserAssignedTasks(String username) {
        return TaskUtils.convertTasks(loadUserByUsername(username).getAssignedTasks());
    }

    public List<TaskResponse> getUserReportedTasks(String username) {
        return TaskUtils.convertTasks(loadUserByUsername(username).getReportedTasks());
    }

}
