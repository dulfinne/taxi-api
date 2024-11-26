package com.dulfinne.taxi.passengerservice.security;

import com.dulfinne.taxi.passengerservice.util.TokenConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtClaimFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    Jwt jwt = ((Jwt) authentication.getPrincipal());
    String username = jwt.getClaimAsString(TokenConstants.USERNAME_CLAIM);

    JwtAuthenticationToken modifiedAuth =
        new JwtAuthenticationToken(jwt, authentication.getAuthorities(), username);
    context.setAuthentication(modifiedAuth);

    filterChain.doFilter(request, response);
  }
}
