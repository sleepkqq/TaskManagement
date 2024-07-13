package com.sleepkqq.taskmanagement.config;

import com.sleepkqq.taskmanagement.service.JwtService;
import com.sleepkqq.taskmanagement.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sleepkqq.taskmanagement.model.enums.SecurityProperties.AUTHORIZATION_HEADER;
import static com.sleepkqq.taskmanagement.model.enums.SecurityProperties.BEARER_PREFIX;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(AUTHORIZATION_HEADER.value());
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX.value())) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.value().length());
        var username = jwtService.extractUsername(jwt);

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = userService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, user)) {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
