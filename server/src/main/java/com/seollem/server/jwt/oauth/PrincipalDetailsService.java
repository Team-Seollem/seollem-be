package com.seollem.server.jwt.oauth;

import com.seollem.server.member.entity.Member;
import com.seollem.server.member.repository.MemberRepository;
import com.seollem.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberService.findVerifiedMemberByEmail(username);
        return new PrincipalDetails(memberEntity);
    }
}
