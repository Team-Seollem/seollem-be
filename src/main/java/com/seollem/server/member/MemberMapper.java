package com.seollem.server.member;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  Member memberPostToMember(MemberDto.Post requestBody);

  Member memberPatchToMember(MemberDto.Patch requestBody);

  MemberDto.GetResponse memberToMemberGetResponse(Member member);

  MemberDto.PatchResponse memberToMemberPatchResponse(Member member);
}
