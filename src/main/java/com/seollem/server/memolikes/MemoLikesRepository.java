package com.seollem.server.memolikes;

import com.seollem.server.memo.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemoLikesRepository extends JpaRepository<MemoLikes, Long> {

  @Query("SELECT COUNT(*) FROM MemoLikes ml WHERE ml.memo = ?1")
  int countMemoLikesWithMemo(Memo memo);
}
