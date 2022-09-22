package com.seollem.server.member.service;

import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import com.seollem.server.member.entity.Member;
import com.seollem.server.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member creatMember(Member member){
        verifyExistsEmail(member.getEmail());

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    public void verifyExistsEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    public Member findMemberByEmail(String email){
        return findVerifiedMemberByEmail(email);
    }

    public Member findVerifiedMemberByEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return member;
    }
}
