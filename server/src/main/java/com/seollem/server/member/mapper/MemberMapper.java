package com.seollem.server.member.mapper;

import com.seollem.server.member.dto.MemberDto;
import com.seollem.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {



    Member memberPostToMember(MemberDto.Post requestBody);

    Member memberPatchToMember(MemberDto.Patch requestBody);

    MemberDto.Response memberToMemberResponse(Member member);

    MemberDto.PatchResponse memberToMemberPatchResponse(Member member);
}
