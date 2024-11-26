package com.dulfinne.taxi.rideservice.security

import com.dulfinne.taxi.rideservice.util.TokenConstants
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtClaimFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val context = SecurityContextHolder.getContext()
        val authentication: Authentication = context.authentication
        val jwt: Jwt = authentication.principal as Jwt
        val username: String = jwt.getClaimAsString(TokenConstants.USERNAME_CLAIM)

        val modifiedAuth = JwtAuthenticationToken(jwt, authentication.authorities, username)
        context.authentication = modifiedAuth

        filterChain.doFilter(request, response)
    }
}