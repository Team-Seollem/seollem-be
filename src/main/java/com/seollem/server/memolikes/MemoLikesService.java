package com.seollem.server.memolikes;

import com.seollem.server.memo.Memo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoLikesService {

  private final MemoLikesRepository memoLikesRepository;

  public int getMemoLikesCountWithMemo(Memo memo) {
    return memoLikesRepository.countMemoLikesWithMemo(memo);
  }
}
