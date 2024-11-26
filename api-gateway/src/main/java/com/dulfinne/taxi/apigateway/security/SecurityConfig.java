package com.dulfinne.taxi.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(
            exchange ->
                exchange
                    .pathMatchers("/api/v1/auth/**")
                    .permitAll()
                    .pathMatchers("/api/v1/rides/driver/**")
                    .hasRole("DRIVER")
                    .pathMatchers("/api/v1/rides/passenger/**")
                    .hasRole("PASSENGER")
                    .pathMatchers("/api/v1/rides/**")
                    .hasRole("ADMIN")
                    .anyExchange()
                    .authenticated())
        .oauth2ResourceServer(
            oauth2 ->
                oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
        .build();
  }

  private ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
    var customConverter = new AuthenticationConverter();
    return new ReactiveJwtAuthenticationConverterAdapter(customConverter);
  }
}
