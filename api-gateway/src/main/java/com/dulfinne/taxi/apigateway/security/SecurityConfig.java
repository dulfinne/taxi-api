package com.dulfinne.taxi.apigateway.security;

import com.dulfinne.taxi.apigateway.util.SecurityConstants;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(
            exchange ->
                exchange
                    .pathMatchers(
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**"
                    ).permitAll()

                    .pathMatchers(
                        "/api/v1/passengers/docs/**",
                        "/api/v1/drivers/docs/**",
                        "/api/v1/rides/docs/**",
                        "/api/v1/promocodes/docs/**",
                        "/api/v1/payments/docs/**",
                        "/api/v1/auth/docs/**")
                    .permitAll()

                    .pathMatchers("/fallback/**").permitAll()

                    .pathMatchers("/api/v1/auth/admin/**")
                    .hasRole("ADMIN")
                    .pathMatchers("/api/v1/auth/**")
                    .permitAll()

                    .pathMatchers("/api/v1/rides/driver/**")
                    .hasRole("DRIVER")
                    .pathMatchers("/api/v1/rides/passenger/**")
                    .hasRole("PASSENGER")
                    .pathMatchers("/api/v1/rides/**")
                    .hasRole("ADMIN")

                    .pathMatchers(HttpMethod.GET, "/api/v1/cars", "/api/v1/cars/{id}")
                    .hasAnyRole("DRIVER", "ADMIN")
                    .pathMatchers("/api/v1/cars", "/api/v1/cars/{id}")
                    .hasRole("ADMIN")
                    .pathMatchers("/api/v1/drivers", "/api/v1/drivers/ratings", "/api/v1/drivers/location")
                    .hasRole("DRIVER")
                    .pathMatchers("/api/v1/drivers/all", "/api/v1/drivers/{username}/**")
                    .hasRole("ADMIN")

                    .pathMatchers("/api/v1/passengers", "/api/v1/passengers/ratings")
                    .hasRole("PASSENGER")
                    .pathMatchers("/api/v1/passengers/{username}/**")
                    .hasRole("ADMIN")

                    .pathMatchers(
                        "/api/v1/payments/wallets",
                        "/api/v1/payments/wallets/credit",
                        "/api/v1/payments/wallets/debit",
                        "/api/v1/payments/wallets/repay-debt")
                    .hasAnyRole("PASSENGER", "DRIVER")
                    .pathMatchers("/api/v1/payments/wallets/{username}/**")
                    .hasRole("ADMIN")
                    .pathMatchers("/api/v1/payments/transactions")
                    .hasAnyRole("PASSENGER", "DRIVER")
                    .pathMatchers("/api/v1/payments/transactions/{username}/**")
                    .hasRole("ADMIN")

                    .pathMatchers("/api/v1/promocodes/**")
                    .hasRole("ADMIN")

                    .anyExchange()
                    .authenticated())
        .oauth2ResourceServer(
            oauth2 ->
                oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
        .build();
  }

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public WebFilter writeableHeaders() {
    return (exchange, chain) -> {
      String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
      if (token == null) {
        return chain.filter(exchange);
      }
      token = token.substring(SecurityConstants.BEARER_LENGTH);

      String username;
      try {
        JWT jwt = JWTParser.parse(token);
        username = jwt.getJWTClaimsSet().getSubject();
      } catch (Exception e) {
        return chain.filter(exchange);
      }

      ServerHttpRequest request = exchange.getRequest();
      HttpHeaders writeableHeaders = HttpHeaders.writableHttpHeaders(request.getHeaders());
      writeableHeaders.add(SecurityConstants.USERNAME_HEADER, username);
      ServerHttpRequestDecorator writeableRequest =
          new ServerHttpRequestDecorator(request) {
            @Override
            public HttpHeaders getHeaders() {
              return writeableHeaders;
            }
          };
      ServerWebExchange writeableExchange = exchange.mutate().request(writeableRequest).build();
      return chain.filter(writeableExchange);
    };
  }

  private ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
    var customConverter = new AuthenticationConverter();
    return new ReactiveJwtAuthenticationConverterAdapter(customConverter);
  }
}
