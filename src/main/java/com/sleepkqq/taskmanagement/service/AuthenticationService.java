package com.sleepkqq.taskmanagement.service;

import com.sleepkqq.taskmanagement.dto.requests.SignInRequest;
import com.sleepkqq.taskmanagement.dto.requests.SignUpRequest;
import com.sleepkqq.taskmanagement.dto.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sleepkqq.taskmanagement.model.User;
import com.sleepkqq.taskmanagement.model.enums.Role;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signUp(SignUpRequest request) {
        var user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(List.of(Role.USER, Role.ADMIN))
                .build();

        userService.create(user);

        return jwtService.generateToken(user);
    }

    public AuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));

        var user = userService.loadUserByUsername(request.username());

        return jwtService.generateToken(user);
    }

}
