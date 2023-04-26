package com.seollem.server.memolikes;

import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoLikesService {

  private final MemoLikesRepository memoLikesRepository;

  public int getMemoLikesCountWithMemo(Memo memo) {
    return memoLikesRepository.countMemoLikesWithMemo(memo);
  }

  public List<Integer> getMemoLikesCountWithMemos(List<Memo> memoList) {
    List<Integer> memoLikesCounts = new ArrayList<>();
    for (int i = 0; i < memoList.size(); i++) {
      memoLikesCounts.add(memoLikesRepository.countMemoLikesWithMemo(memoList.get(i)));
    }
    return memoLikesCounts;
  }

  public List<MemoLikes> findMemoLikesDone(List<Memo> memoList, Member member) {
    List<MemoLikes> result = memoLikesRepository.findMemoLikesDone(memoList, member);
    return result;
  }
}
