package com.seollem.server.memo;

import com.seollem.server.book.Book;
import com.seollem.server.member.Member;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookMemoDto;
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

  List<Memo> findAllByBookAndMemoAuthority(Book book, MemoAuthority memoAuthority);

  Page<Memo> findAllByBookAndMemoAuthority(Pageable pageable, Book book,
      MemoAuthority memoAuthority);


  Page<Memo> findAllByBook(Pageable pageable, Book book);

  @Query(value = "SELECT * FROM memo WHERE MEMBER_ID = ?1 order by RAND() limit 1", nativeQuery = true)
  Memo findRandomMemo(Member member);

  @Query("SELECT COUNT(*) FROM Memo m WHERE m.book = ?1")
  int countMemoWithBook(Book book);

  @Query("SELECT COUNT(*) FROM Memo m WHERE m.member = ?1")
  int countMemoWithMember(Member member);

  @Query("SELECT COUNT(*) FROM Memo m WHERE m.book = ?1 and m.memoAuthority = ?2")
  int countMemoWithBookAndMemoAuthority(Book book, MemoAuthority memoAuthority);

  @Query("SELECT new com.seollem.server.member.dto.othermemberbook.OtherMemberBookMemoDto(m.memoId, m.memoType, m.memoContent, m.memoBookPage) FROM Memo m WHERE m.book = ?1 and m.memoAuthority = 'PUBLIC' ")
  Page<OtherMemberBookMemoDto> findAllOtherMemberBookMemosWithBook(Pageable pageable, Book book);
}
