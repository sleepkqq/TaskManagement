package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.responses.AuthenticationResponse;
import com.sleepkqq.taskmanagement.dto.requests.SignInRequest;
import com.sleepkqq.taskmanagement.dto.requests.SignUpRequest;
import com.sleepkqq.taskmanagement.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User Registration")
    @PostMapping("/sign-up")
    public AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "User Authorization")
    @PostMapping("/sign-in")
    public AuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

}
