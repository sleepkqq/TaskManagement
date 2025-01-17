package com.sleepkqq.taskmanagement.config;

import com.sleepkqq.taskmanagement.service.JwtService;
import com.sleepkqq.taskmanagement.service.UserService;
import io.vertx.core.json.JsonObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sleepkqq.taskmanagement.constants.ExceptionResponseProperties.ERROR;
import static com.sleepkqq.taskmanagement.constants.RestProperties.APPLICATION_JSON;
import static com.sleepkqq.taskmanagement.constants.SecurityProperties.AUTHORIZATION_HEADER;
import static com.sleepkqq.taskmanagement.constants.SecurityProperties.BEARER_PREFIX;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var jwt = authHeader.substring(BEARER_PREFIX.length());

            var username  = jwtService.extractUsername(jwt);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                var user = userService.loadUserByUsername(username);

                var authentication = new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(new JsonObject().put(ERROR, e.getMessage()).toString());
        }
    }

}
