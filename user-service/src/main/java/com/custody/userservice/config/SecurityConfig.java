package com.custody.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
  .cors(Customizer.withDefaults()) // ✅ Enable CORS
  .csrf(AbstractHttpConfigurer::disable)
  .authorizeHttpRequests(auth -> auth
    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ Preflight
    .anyRequest().authenticated()
  )
  .oauth2ResourceServer(oauth2 -> oauth2
    .jwt(jwt -> jwt
      .jwtAuthenticationConverter(jwtAuthenticationConverter())
    )
  );

    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    // Replace with your actual JWK Set URI (e.g., from Google or Okta)
    String jwkSetUri = "https://www.googleapis.com/oauth2/v3/certs";
    return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
  }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    // Optionally configure authorities converter
    return converter;
  }
}
