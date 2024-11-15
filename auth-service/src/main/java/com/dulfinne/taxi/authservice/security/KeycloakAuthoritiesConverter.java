package com.dulfinne.taxi.authservice.security;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class KeycloakAuthoritiesConverter {

  private final JwtGrantedAuthoritiesConverter defaultConverter =
      new JwtGrantedAuthoritiesConverter();

  public Set<GrantedAuthority> convert(Jwt jwt) {
    Set<GrantedAuthority> authorities = new HashSet<>(defaultConverter.convert(jwt));

    var realmRoles = jwt.getClaimAsStringList("spring_sec_roles");
    authorities.addAll(
        realmRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));

    return authorities;
  }
}
