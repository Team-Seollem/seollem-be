package com.seollem.server.memolike;

import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemoLikeService {

  private final MemoLikeRepository memoLikeRepository;
  private final MemoLikeMapper memoLikeMapper;


  public MemoLike findVerifiedMemoLikeById(long memoLikeId) {
    Optional<MemoLike> optionalMemoLike = memoLikeRepository.findById(memoLikeId);
    MemoLike memoLike = optionalMemoLike.orElseThrow(
        () -> new BusinessLogicException(ExceptionCode.MEMOLIKE_NOT_FOUND));
    return memoLike;
  }

  public void verifyMemberHasMemoLike(Member member, MemoLike memoLike) {
    if (!(memoLike.getMember() == member)) {
      throw new BusinessLogicException(ExceptionCode.NOT_MEMBER_MEMOLIKE);
    }
  }


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

  public void createMemoLike(Memo memo, Member member) {
    // 해당 메모의 메모좋아요 객체 중에서 이미 해당 회원의 객체가 있는 지 확인합니다.
    List<MemoLike> memoLikes = memoLikeRepository.findAllByMemoAndMember(memo, member);
    if (!memoLikes.isEmpty()) {
      throw new BusinessLogicException(ExceptionCode.MEMOLIKE_ALREADY_DONE);
    } else {
      MemoLike memoLike = memoLikeMapper.toMemoLike(memo, member);
      memoLikeRepository.save(memoLike);
    }
  }

  public void deleteMemoLikeWithMemoLike(MemoLike memoLike) {
    memoLikeRepository.delete(memoLike);
  }


}
