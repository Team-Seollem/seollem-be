package com.seollem.server.jjwt.service;

import com.seollem.server.jjwt.utils.RoleAuthorityUtils;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//JWT 자격 증명을 위한 로그인 인증 구현
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberService memberService;
    private final RoleAuthorityUtils authorityUtils;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.findVerifiedMemberByEmail(username);
      //  Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return new PrincipalDetails(member);
    }

    // UserDetailsService(MemberDetailsService)가 사용자의 크리덴셜을 DB에서 조회한 후, AuthenticationManager에게 사용자의 UserDetails를 전달
    public class PrincipalDetails extends Member implements UserDetails {

        PrincipalDetails(Member member) {
            setMemberId(member.getMemberId());
            setName(member.getName());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities((this.getRoleList())); //DB에 저장된 Role 정보로 User 권한 목록 생성
        }


        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }


}
