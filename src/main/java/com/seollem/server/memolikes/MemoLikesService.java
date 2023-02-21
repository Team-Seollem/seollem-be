package com.seollem.server.memolikes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoLikesService {

  private final MemoLikesRepository memoLikesRepository;

  public long getMemoLikesCount() {
    return memoLikesRepository.countMemoLikes();
  }
}
