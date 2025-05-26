package me.alewand.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;
    private final NonAuthenticatedHandler nonAuthenticatedHandler;
    private final NonAuthorizedHandler nonAuthorizedHandler;

    public SecurityConfig(AuthenticationFilter authenticationFilter,
            NonAuthenticatedHandler nonAuthenticatedHandler,
            NonAuthorizedHandler nonAuthorizedHandler) {
        this.authenticationFilter = authenticationFilter;
        this.nonAuthenticatedHandler = nonAuthenticatedHandler;
        this.nonAuthorizedHandler = nonAuthorizedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(nonAuthenticatedHandler)
                        .accessDeniedHandler(nonAuthorizedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/refresh").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
