package com.seollem.server.member;

import com.seollem.server.member.dto.MemberDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookMemoDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookResponseDto;
import com.seollem.server.memolikes.MemoLikes;
import java.util.List;
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

  default OtherMemberBookResponseDto toOtherMemberBookResponseDto(
      OtherMemberBookDto otherMemberBookDto, List<OtherMemberBookMemoDto> otherMemberBookMemoDtos,
      List<MemoLikes> memoLikes,
      List<Integer> memoLikesCount, int memoCount) {
    OtherMemberBookResponseDto result = new OtherMemberBookResponseDto();
    result.setTitle(otherMemberBookDto.getTitle());
    result.setAuthor(otherMemberBookDto.getAuthor());
    result.setCover(otherMemberBookDto.getCover());
    result.setPublisher(otherMemberBookDto.getPublisher());
    result.setItemPage(otherMemberBookDto.getItemPage());
    result.setCurrentPage(otherMemberBookDto.getCurrentPage());
    result.setStar(otherMemberBookDto.getStar());
    result.setBookStatus(otherMemberBookDto.getBookStatus());
    for (int i = 0; i < otherMemberBookMemoDtos.size(); i++) {
      otherMemberBookMemoDtos.get(i).setMemoLikesCount(memoLikesCount.get(i));
    }
    for (OtherMemberBookMemoDto otherMemberBookMemoDtoItem : otherMemberBookMemoDtos) {
      for (MemoLikes memoLikesItem : memoLikes) {
        if (otherMemberBookMemoDtoItem.getMemoId() == memoLikesItem.getMemo().getMemoId()) {
          otherMemberBookMemoDtoItem.setMemoLikeDone(true);
        }
      }
    }
    result.setMemoList(otherMemberBookMemoDtos);
    result.setMemoCount(memoCount);

    return result;
  }
}
