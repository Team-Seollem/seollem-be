package com.seollem.server.memolike;

import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemoLikeRepository extends JpaRepository<MemoLike, Long> {

  @Query("SELECT COUNT(*) FROM MemoLike ml WHERE ml.memo = ?1")
  int countMemoLikesWithMemo(Memo memo);

  @Query("SELECT COUNT(ml.memoLikeId) FROM MemoLike ml WHERE ml.memo in (?1)")
  List<Integer> countMemoLikesWithMemos(List<Memo> memoList);

  @Query(value = "select ml from MemoLike ml where ml.memo in (?1) and ml.member = ?2")
  List<MemoLike> findMemoLikesDone(List<Memo> memoList, Member member);

  List<MemoLike> findAllByMemoAndMember(Memo memo, Member member);

  List<MemoLike> findAllByMemberAndMemoLikeId(Member member, long memoLikeId);

}
