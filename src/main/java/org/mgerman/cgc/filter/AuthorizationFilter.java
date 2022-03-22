package org.mgerman.cgc.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (request.getServletPath().equals("/login")) {
      filterChain.doFilter(request, response);
      return;
    }

    String authHeader = request.getHeader(AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String token = authHeader.substring("Bearer ".length());
      JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("secret".getBytes())).build();
      DecodedJWT decodedJWT = jwtVerifier.verify(token);
      String username = decodedJWT.getSubject();
      String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

      var authenticationToken = new UsernamePasswordAuthenticationToken(username, null,
          Stream.of(roles).map(SimpleGrantedAuthority::new).toList());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      filterChain.doFilter(request, response);
    } catch (Exception ex) {
      log.error("Error logging in: {}", ex.getMessage());
      response.setHeader("error", ex.getMessage());
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      new ObjectMapper().writeValue(response.getOutputStream(),
          Map.of("error_message", ex.getMessage()));
    }

  }
}
