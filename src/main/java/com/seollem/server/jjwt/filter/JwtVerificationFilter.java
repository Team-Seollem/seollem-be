package com.seollem.server.jjwt.filter;

import com.seollem.server.jjwt.jwt.JwtTokenizer;
import com.seollem.server.jjwt.service.TokenService;
import com.seollem.server.jjwt.utils.RoleAuthorityUtils;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
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

  private final TokenService tokenService;

  private final MemberService memberService;




  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String servletPath = request.getServletPath();
    String accessToken = jwtTokenizer.resolveAccessToken(request);
    String refreshToken = jwtTokenizer.resolveRefreshToken(request);

//    if (servletPath.equals("/refresh")) {
//      filterChain.doFilter(request, response);
//    } else if (){
    if (accessToken != null) {
      // 어세스 토큰이 유효한 상황

          try {
              Map<String, Object> claims = verifyJws(request);
              setAuthenticationToContext(claims);
            }
          catch (SignatureException se) {
              request.setAttribute("exception", se);
            } catch (ExpiredJwtException ee) {

            if (!jwtTokenizer.validateToken(accessToken) && refreshToken != null){
              boolean validateRefreshToken = jwtTokenizer.validateToken(refreshToken);
              // 리프레시 토큰 저장소 존재유무 확인
              boolean isRefreshToken = jwtTokenizer.existsRefreshToken(refreshToken);
              if (validateRefreshToken && isRefreshToken) {
                /// 리프레시 토큰으로 이메일 정보 가져오기
                String email = jwtTokenizer.getUserEmail(refreshToken);

                Member member = memberService.findVerifiedMemberByEmail(email);
                /// 이메일로 권한정보 받아오기
                //Object roles = jwtTokenizer.getRoles(email).toString();

                //Map<String, Object> claim = new HashMap<>(email,roles);

                // 토큰 발급
                //String newAccessToken = jwtTokenizer.generateAccessToken(claims,email,expiration,key);
                //
                String newAccessToken = tokenService.delegateAccessToken(member);

                /// 헤더에 어세스 토큰 추가
                jwtTokenizer.setHeaderAccessToken(response, newAccessToken);
                /// 컨텍스트에 넣기
//                        this.setAuthentication(newAccessToken);

              }
            }

          } catch (Exception e) {
              request.setAttribute("exception", e);
            }
    }

    filterChain.doFilter(request, response);
  }
  //예외가 발생하게되면 SecurityContext에 클라이언트의 인증 정보(Authentication 객체)가 저장되지 않습니다.


   public void setAuthentication(String token) {
          // 토큰으로부터 유저 정보를 받아옵니다.
          Authentication authentication = jwtTokenizer.getAuthentication(token);
          // SecurityContext 에 Authentication 객체를 저장합니다.
          SecurityContextHolder.getContext().setAuthentication(authentication);
      }




  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String authorization = request.getHeader("Authorization");

    return authorization == null || !authorization.startsWith("Bearer");
  }

  //JWT를 검증
  private Map<String, Object> verifyJws(HttpServletRequest request) {
    String jws = request.getHeader("Authorization").replace("Bearer ", "");
    String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
    Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

    return claims;
  }

  private void setAuthenticationToContext(Map<String, Object> claims) {
    String email = (String) claims.get("email");
    List<GrantedAuthority> authorities =
        roleAuthorityUtils.createAuthorities((List) claims.get("roles"));
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(email, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}