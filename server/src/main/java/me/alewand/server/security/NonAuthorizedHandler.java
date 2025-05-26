package me.alewand.server.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NonAuthorizedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(403);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Brak uprawnień.\"}");
    }
}
