package com.seollem.server.JWT_Test;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.util.*;

import com.seollem.server.jjwt.jwt.JwtTokenizer;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenizerTest {
  private static JwtTokenizer jwtTokenizer;
  private String secretKey;
  private String base64EncodedSecretKey;

  @BeforeAll
  public void init(){
    //jwtTokenizer = new JwtTokenizer();
    secretKey = "sellemJJWT";

    base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);
  }

  @Test
  public void encodeBase64SecretKeyTest(){
    System.out.println(base64EncodedSecretKey);

    assertThat
        (secretKey, is(new String(Decoders.BASE64.decode(base64EncodedSecretKey))));
  }

  @Test
  public void generateAccessTokenTest(){
    Map<String , Object> claims = new HashMap<>();
    claims.put("memberId",1);
    claims.put("roles",List.of("ROLE_USER"));

    String subject = "Test access Token";
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE,1);
    Date expiration = calendar.getTime();

    String accessToken = jwtTokenizer.generateAccessToken(claims,subject,expiration,base64EncodedSecretKey);

    System.out.println(accessToken);

    assertThat(accessToken, notNullValue());
  }

  @Test
  public void generateRefreshTokenTest() {
    String subject = "test refresh token";
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, 1);
    Date expiration = calendar.getTime();

    String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

    System.out.println(refreshToken);

    assertThat(refreshToken, notNullValue());
  }

 @DisplayName("어떤 예외도 발생하지않음 ")
  @Test
  public void verifySignatureTest(){
   String accessToken = getAccessToken(Calendar.MINUTE,1);
   assertDoesNotThrow(()->jwtTokenizer.verifySignature(accessToken,base64EncodedSecretKey));
 }

 @DisplayName("유효시간 초과")
  @Test
  public  void  verifyExpirationTest() throws  InterruptedException{
    String accessToken = getAccessToken(Calendar.SECOND,1);
    assertDoesNotThrow(()->jwtTokenizer.verifySignature(accessToken,base64EncodedSecretKey));

   TimeUnit.MILLISECONDS.sleep(1500);
 }

 private String getAccessToken(int timeUnit,int timeAmount){
    Map<String , Object> claims = new HashMap<>();
   claims.put("memberId", 1);
   claims.put("roles", List.of("USER"));

   String subject = "test access token";
   Calendar calendar = Calendar.getInstance();
   calendar.add(timeUnit, timeAmount);
   Date expiration = calendar.getTime();
   String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

   return accessToken;
 }

}