package me.alewand.server.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.alewand.server.errors.UserNotFoundException;
import me.alewand.server.services.TokenService;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Brak wymaganego tokena autoryzacji.");
        }

        String accessToken = authHeader.substring(7);

        try {
            var authentication = tokenService.verifyAccessToken(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException | UserNotFoundException ex) {
            throw new BadCredentialsException("Nieprawidłowy lub wygasły token autoryzacji.");
        }

        filterChain.doFilter(request, response);
    }

}
