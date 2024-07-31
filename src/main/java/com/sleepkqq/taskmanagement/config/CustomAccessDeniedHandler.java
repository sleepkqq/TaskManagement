package com.sleepkqq.taskmanagement.config;

import io.vertx.core.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.sleepkqq.taskmanagement.constants.ExceptionResponseProperties.ERROR;
import static com.sleepkqq.taskmanagement.constants.RestProperties.APPLICATION_JSON;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(new JsonObject().put(ERROR, accessDeniedException.getMessage()).toString());
    }

}
