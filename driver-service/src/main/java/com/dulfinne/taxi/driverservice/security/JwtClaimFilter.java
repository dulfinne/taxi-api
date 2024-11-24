package com.dulfinne.taxi.driverservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtClaimFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Jwt jwt = ((Jwt) authentication.getPrincipal());
    String username = jwt.getClaimAsString("preferred_username");

    JwtAuthenticationToken modifiedAuth =
        new JwtAuthenticationToken(jwt, authentication.getAuthorities(), username);
    SecurityContextHolder.getContext().setAuthentication(modifiedAuth);

    filterChain.doFilter(request, response);
  }
}
