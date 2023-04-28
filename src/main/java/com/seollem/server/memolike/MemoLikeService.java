package com.seollem.server.memolike;

import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoLikeService {

  private final MemoLikeRepository memoLikeRepository;
  private final MemoLikeMapper memoLikeMapper;

  public int getMemoLikesCountWithMemo(Memo memo) {
    return memoLikeRepository.countMemoLikesWithMemo(memo);
  }

  public List<Integer> getMemoLikesCountWithMemos(List<Memo> memoList) {
    List<Integer> memoLikesCounts = new ArrayList<>();
    for (int i = 0; i < memoList.size(); i++) {
      memoLikesCounts.add(memoLikeRepository.countMemoLikesWithMemo(memoList.get(i)));
    }
    return memoLikesCounts;
  }

  public List<MemoLike> findMemoLikesDone(List<Memo> memoList, Member member) {
    List<MemoLike> result = memoLikeRepository.findMemoLikesDone(memoList, member);
    return result;
  }

  public void createLike(Memo memo, Member member) {
    // 해당 메모의 메모좋아요 객체 중에서 이미 해당 회원의 객체가 있는 지 확인합니다.
    List<MemoLike> memoLikes = memoLikeRepository.findAllByMemoAndMember(memo, member);
    if (!memoLikes.isEmpty()) {
      throw new BusinessLogicException(ExceptionCode.MEMOLIKE_ALREADY_DONE);
    } else {
      MemoLike memoLike = memoLikeMapper.toMemoLike(memo, member);
      memoLikeRepository.save(memoLike);
    }
  }
}
