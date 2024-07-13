package com.sleepkqq.taskmanagement.service;

import com.sleepkqq.taskmanagement.model.User;
import com.sleepkqq.taskmanagement.model.enums.Role;
import com.sleepkqq.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        return save(user);
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

}
