package com.seollem.server.jjwt.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RoleAuthorityUtils {

  private final List<GrantedAuthority> ADMIN_ROLES =
      AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
  private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
  private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
  private final List<String> USER_ROLES_STRING = List.of("USER");
  //@Value("${mail.address.admin}")
  private String adminMailAddress;

  //메모리에있는 Role을 기반으로 권한 정보 생성
//    public List<GrantedAuthority> createAuthorities(String email) {
//        if (email.equals(adminMailAddress)) {
//            return ADMIN_ROLES;
//        }
//        return USER_ROLES;
//    }

  //DB에 저장된 Role을 기반으로 권한 정보 생성
  public List<GrantedAuthority> createAuthorities(List<String> roles) {
    List<GrantedAuthority> authorities = roles.stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
    return authorities;
  }

  //DB 저장 (회원의 Role정보를 생성)
  public List<String> createRoles(String email) {
    if (email.equals(adminMailAddress)) {
      return ADMIN_ROLES_STRING;
    }
    return USER_ROLES_STRING;
  }

}

