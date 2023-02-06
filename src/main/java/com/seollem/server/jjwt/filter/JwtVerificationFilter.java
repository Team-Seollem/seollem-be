package com.seollem.server.jjwt.filter;

import com.seollem.server.jjwt.jwt.JwtTokenizer;
import com.seollem.server.jjwt.utils.RoleAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

//클라이언트 측에서 전송된 request header에 포함된 JWT에 대해 검증 작업을 수행하는 JwtVerificationFilter의 코드
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

  private final JwtTokenizer jwtTokenizer;
  private final RoleAuthorityUtils roleAuthorityUtils;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      Map<String, Object> claims = verifyJws(request);
      setAuthenticationToContext(claims);
    } catch (SignatureException se) {
      request.setAttribute("exception", se);
    } catch (ExpiredJwtException ee) {
      request.setAttribute("exception", ee);
    } catch (Exception e) {
      request.setAttribute("exception", e);
    }

    filterChain.doFilter(request, response);
  }


  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String authorization = request.getHeader("Authorization");

    return authorization == null || !authorization.startsWith("Bearer");
  }

  private Map<String, Object> verifyJws(HttpServletRequest request) {
    String jws = request.getHeader("Authorization").replace("Bearer ", "");
    String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
    Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

    return claims;
  }

  private void setAuthenticationToContext(Map<String, Object> claims) {
    String username = (String) claims.get("username");
    List<GrantedAuthority> authorities =
        roleAuthorityUtils.createAuthorities((List) claims.get("roles"));
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(username, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}