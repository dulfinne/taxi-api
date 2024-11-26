package com.dulfinne.taxi.rideservice.security

import com.dulfinne.taxi.rideservice.util.TokenConstants
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors

class AuthoritiesConverter {
    private val converter = JwtGrantedAuthoritiesConverter()

    fun convert(jwt: Jwt): Set<GrantedAuthority> {
        val authorities = HashSet(converter.convert(jwt))

        val realmRoles = jwt.getClaimAsStringList(TokenConstants.ROLES_CLAIM)
        val grantedAuthorities = realmRoles.stream()
            .map { SimpleGrantedAuthority(it) }
            .collect(Collectors.toSet())

        authorities.addAll(grantedAuthorities)
        return authorities
    }
}
