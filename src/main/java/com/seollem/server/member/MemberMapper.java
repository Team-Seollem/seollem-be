package com.seollem.server.member;

import com.seollem.server.member.dto.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

  default Member memberPostToMember(MemberDto.Post requestBody) {
    if (requestBody == null) {
      return null;
    }

    Member member = new Member();

    member.setEmail(requestBody.getEmail());
    member.setName(requestBody.getName());
    member.setPassword(requestBody.getPassword());

    return member;
  }

  Member memberPatchToMember(MemberDto.Patch requestBody);

  MemberDto.GetResponse memberToMemberGetResponse(Member member);

  MemberDto.PatchResponse memberToMemberPatchResponse(Member member);
}
