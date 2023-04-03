package com.seollem.server.jjwt.config;

import com.seollem.server.jjwt.filter.JwtAuthenticationFilter;
import com.seollem.server.jjwt.filter.JwtVerificationFilter;
import com.seollem.server.jjwt.handler.MemberAccessDeniedHandler;
import com.seollem.server.jjwt.handler.MemberAuthenticationEntryPoint;
import com.seollem.server.jjwt.handler.MemberAuthenticationFailureHandler;
import com.seollem.server.jjwt.handler.MemberAuthenticationSuccessHandler;
import com.seollem.server.jjwt.jwt.JwtTokenizer;
import com.seollem.server.jjwt.service.TokenService;
import com.seollem.server.jjwt.utils.RoleAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

  @Autowired
  private CorsConfig corsConfig;

  private final JwtTokenizer jwtTokenizer;

  private final RoleAuthorityUtils authorityUtils;

  private final TokenService tokenService;


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .headers().frameOptions().disable()// x-frame-option
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
        .accessDeniedHandler(new MemberAccessDeniedHandler())
        .and()
        .apply(new CustomFilterConfigurer()) // 추가
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

  public class CustomFilterConfigurer
      extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) throws Exception {
      AuthenticationManager authenticationManager =
          builder.getSharedObject(AuthenticationManager.class);

      JwtAuthenticationFilter jwtAuthenticationFilter =
          new JwtAuthenticationFilter(authenticationManager, tokenService);
      jwtAuthenticationFilter.setAuthenticationSuccessHandler(
          new MemberAuthenticationSuccessHandler());
      jwtAuthenticationFilter.setAuthenticationFailureHandler(
          new MemberAuthenticationFailureHandler());

      JwtVerificationFilter
          jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

      builder.addFilter(corsConfig.corsFilter())
          .addFilter(jwtAuthenticationFilter)
          .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
  }
}
