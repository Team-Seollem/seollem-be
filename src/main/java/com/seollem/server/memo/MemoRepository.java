package com.seollem.server.memo;

import com.seollem.server.book.Book;
import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo.MemoAuthority;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemoRepository extends JpaRepository<Memo, Long> {

  Optional<Memo> findById(long memoId);

  List<Memo> findAllByBook(Book book);

  Page<Memo> findAllByBookAndMemoType(Pageable pageable, Book book, Memo.MemoType memoType);

  List<Memo> findAllByMemoAuthority(MemoAuthority memoAuthority);

  Page<Memo> findAllByBook(Pageable pageable, Book book);

  @Query(value = "SELECT * FROM memo WHERE MEMBER_ID = ?1 order by RAND() limit 1", nativeQuery = true)
  Memo findRandomMemo(Member member);
}
