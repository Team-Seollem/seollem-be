package com.seollem.server.memolikes;

import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemoLikesRepository extends JpaRepository<MemoLikes, Long> {

  @Query("SELECT COUNT(*) FROM MemoLikes ml WHERE ml.memo = ?1")
  int countMemoLikesWithMemo(Memo memo);

  @Query("SELECT COUNT(ml.memoLikesId) FROM MemoLikes ml WHERE ml.memo in (?1)")
  List<Integer> countMemoLikesWithMemos(List<Memo> memoList);

  @Query(value = "select ml from MemoLikes ml where ml.memo in (?1) and ml.member = ?2")
  List<MemoLikes> findMemoLikesDone(List<Memo> memoList, Member member);
}
