package me.alewand.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

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
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedOrigin("http://localhost:5173");
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                config.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                .cors(withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(e -> e
                                                .authenticationEntryPoint(nonAuthenticatedHandler)
                                                .accessDeniedHandler(nonAuthorizedHandler))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/login", "/api/auth/register",
                                                                "/api/auth/refresh")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}
