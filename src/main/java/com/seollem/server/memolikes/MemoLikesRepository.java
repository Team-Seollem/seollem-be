package com.seollem.server.memolikes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemoLikesRepository extends JpaRepository<MemoLikes, Long> {

  @Query("SELECT COUNT(*) FROM MemoLikes ml")
  Long countMemoLikes();
}
