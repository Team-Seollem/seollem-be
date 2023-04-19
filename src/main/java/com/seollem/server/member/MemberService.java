package com.seollem.server.member;

import com.seollem.server.emailauth.EmailRedisUtil;
import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final EmailRedisUtil redisUtil;

  public Member createMember(Member member, String authenticationCode) {
    verifyExistsEmail(member.getEmail());
    verifyValidAuthenicationCode(member.getEmail(), authenticationCode);
    member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
    member.setRoles("ROLE_USER");
    Member savedMember = memberRepository.save(member);

    return savedMember;
  }

  public Member updateMember(Member member) {
    Member findMember = findVerifiedMemberByEmail(member.getEmail());
    Optional.ofNullable(member.getName()).ifPresent(name -> findMember.setName(name));
    Optional.ofNullable(member.getPassword())
        .ifPresent(password -> findMember.setPassword(bCryptPasswordEncoder.encode(password)));
    Optional.ofNullable(member.getUrl()).ifPresent(url ->findMember.setUrl(url));

    return memberRepository.save(findMember);
  }

  public void deleteMember(String email) {
    Member findMember = findVerifiedMemberByEmail(email);
    memberRepository.delete(findMember);
  }

  public void verifyExistsEmail(String email) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    if (optionalMember.isPresent()) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
  }

  public Member findVerifiedMemberByEmail(String email) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    Member member = optionalMember.orElseThrow(
        () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

    return member;
  }

  public void verifyValidAuthenicationCode(String email, String authenticationCode) {

    if (!redisUtil.getData(email).equals(authenticationCode)) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_AUTHENTICATIONCODE_INVALID);
    }
  }

}
