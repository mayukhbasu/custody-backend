package com.custody.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class NoSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())         // ✅ Lambda DSL syntax for CSRF
            .formLogin(form -> form.disable())    // ✅ Disable form login
            .httpBasic(basic -> basic.disable()); // ✅ Disable basic auth

        return http.build();
    }
}
  