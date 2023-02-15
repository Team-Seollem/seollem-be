package com.seollem.server.jjwt.jwt;

import com.seollem.server.emailauth.EmailRedisUtil;
import com.seollem.server.jjwt.service.PrincipalDetailsService;
import com.seollem.server.member.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component // bean등록
public class JwtTokenizer {

    //로그인 인증에 성공한 클라이언트에게 JWT를 생성 및 발급하고 클라이언트의 요청이 들어올 때 마다 전달된 JWT를 검증하는 역할을 합니다.
    //jwt 생성시 필요한 정보
    @Getter
    @Value("${jwt-secret-key}") //jwt 생성 및 검증시 사용 되는 secret key정보
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}") //토큰 만료시간정보
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-time}")
    private int refreshTokenExpirationDays;

    private  final PrincipalDetailsService principalDetailsService;

    private final EmailRedisUtil emailRedisUtil;

    private final MemberRepository memberRepository;



    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

//    @PostConstruct
//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }

//인증된 사용자에게 JWT를 최초로 발급해주기 위한 JWT 생성 메서드
    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }

    public String generateRefreshToken(String subject, Date expiration,
                                       String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }


    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jws);
        return claims;
    }

    //parserbuilder를 이용해서 parseClaimsJws 매서드를 이용해서 토큰 jws로 parse한다
    //test용
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jws);
    }

    //jwt의 만료일시를 지정하기위한 매서드 jwt 생성 시 사용
    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    public Date getRefreshTokenExpiration(int expirationDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, expirationDays);
        Date expiration = calendar.getTime();

        return expiration;
    }

    //JWT의 서명에 사용할 Secret Key를 생성
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

        // Request의 Header에서 AccessToken 값을 가져옵니다. "authorization" : "token'
        public String resolveAccessToken(HttpServletRequest request) {
            if (request.getHeader("Authorization") != null)
                return request.getHeader("Authorization");
            return null;
        }

        // Request의 Header에서 RefreshToken 값을 가져옵니다. "authorization" : "token'
        public String resolveRefreshToken(HttpServletRequest request) {
            if (request.getHeader("RefreshToken") != null)
                return request.getHeader("RefreshToken");
            return null;
        }




    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getKeyFromBase64EncodedKey(secretKey)).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.info(e.getMessage() + "토큰 만료" );
            return false;
        }
    }


    // JWT 에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails =
            principalDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }


    // RefreshToken 존재유무 확인
    public boolean existsRefreshToken(String refreshToken) {
        return emailRedisUtil.getData(refreshToken) != null;
    }

    // 토큰에서 회원 정보 추출
    public String getUserEmail(String token) {

        return Jwts.parserBuilder().setSigningKey(getKeyFromBase64EncodedKey(secretKey)).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Email로 권한 정보 가져오기
    public List<String> getRoles(String email) {
        //return memberService.findVerifiedMemberByEmail(email).get().getRoles();
        return memberRepository.findByEmail(email).get().getRoleList();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("authorization", "bearer "+ accessToken);
    }


}



