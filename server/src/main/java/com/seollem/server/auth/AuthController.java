package com.seollem.server.auth;

import com.seollem.server.member.entity.Member;
import com.seollem.server.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthController(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*
        로그인 요청의 경우 UsernamePasswordAuthenticationFilter 에서 PathMatcher 로 /login 과 POST 를 지정하고 있다.
        따라서 "/login" 으로 요청할 경우 자동으로 인터셉트 되어 로그인이 진행되므로 따로 컨트롤러는 작성하지 않는다.
     */


    @PostMapping("/join")
    public String join(@RequestBody Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        memberRepository.save(member);
        return "회원 가입 완료";
    }
}
