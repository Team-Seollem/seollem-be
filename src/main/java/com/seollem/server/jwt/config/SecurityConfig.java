package com.seollem.server.jwt.config;

import com.seollem.server.jwt.decoder.TokenDecodeService;
import com.seollem.server.jwt.filter.JwtAuthenticationFilter;
import com.seollem.server.jwt.filter.JwtAuthorizationFilter;
import com.seollem.server.member.MemberService;
import com.seollem.server.util.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsFilter corsFilter;
  private final MemberService memberService;
  private final TokenDecodeService tokenDecodeService;
  private final Keys keys;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.headers().frameOptions().disable(); // x-frame-option
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .apply(new CustomDsl()) // 추가
        .and()
        .authorizeRequests()
        .antMatchers("/join")
        .permitAll()
        .antMatchers("/login")
        .permitAll()
        .antMatchers("/ext-lib/**")
        .permitAll()
        .antMatchers("/email/**")
        .permitAll()
        //                .antMatchers("/api/v1/admin/**")
        //                .access("hasRole('ROLE_ADMIN')")
        .anyRequest()
        .access("hasRole('ROLE_USER')");
    return http.build();
  }

  public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) throws Exception {
      AuthenticationManager authenticationManager =
          builder.getSharedObject(AuthenticationManager.class);
      builder.addFilter(corsFilter)
          .addFilter(new JwtAuthenticationFilter(authenticationManager, keys))
          .addFilter(
              new JwtAuthorizationFilter(
                  authenticationManager, memberService, tokenDecodeService));
    }
  }
}
