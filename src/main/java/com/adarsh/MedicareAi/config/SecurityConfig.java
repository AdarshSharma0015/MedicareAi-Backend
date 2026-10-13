package com.adarsh.MedicareAi.config;

import com.adarsh.MedicareAi.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(customizer -> customizer.disable());

        http.authorizeHttpRequests(customizer -> customizer

                .requestMatchers(
                        "/api/auth/register",
                        "/api/auth/login"
                ).permitAll()

                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                .requestMatchers("/api/doctor/**")
                .hasRole("DOCTOR")

                .requestMatchers("/api/patient/**")
                .hasRole("PATIENT")

                .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
// This is for running JwtFilter before Springs UsernamePasswordAuthenticationFilter
        return http.build();
    }
}