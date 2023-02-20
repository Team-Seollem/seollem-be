package com.seollem.server.memolikes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemoLikesService {

  private final MemoLikesRepository memoLikesRepository;

  public long getMemoLikesCount() {
    return memoLikesRepository.countMemoLikes();
  }
}
