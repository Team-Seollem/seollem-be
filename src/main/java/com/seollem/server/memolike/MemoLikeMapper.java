package com.seollem.server.memolike;

import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemoLikeMapper {

  default MemoLike toMemoLike(Memo memo, Member member) {
    if (memo == null && member == null) {
      return null;
    }

    MemoLike memoLike = new MemoLike();

    if (memo != null) {
      memoLike.setMemo(memo);
    }

    if (member != null) {
      memoLike.setMember(member);
    }

    return memoLike;
  }
}
