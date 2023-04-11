package com.seollem.server.book;

import com.seollem.server.member.Member;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findById(long bookId);

  @Query(value = "SELECT b FROM Book b WHERE b.title = :title AND b.member.memberId = :memberId")
  Optional<Book> findByTitle(String title, long memberId);

  //    Optional<List<Book>>  findByMember(Member member);

  @Query(
      value =
          "SELECT * FROM book WHERE MEMBER_ID = ?1 AND BOOK_STATUS = ?2 AND CREATED_AT > ?3 AND CREATED_AT < ?4",
      countQuery =
          "SELECT count(*) FROM book WHERE MEMBER_ID = ?1 AND BOOK_STATUS = ?2 AND CREATED_AT > ?3 AND CREATED_AT < ?4",
      nativeQuery = true)
  Optional<Page<Book>> findCalender(
      Member member, Book.BookStatus bookStatus, LocalDateTime before, LocalDateTime after,
      Pageable pageable);

  Page<Book> findAllByMemberAndBookStatus(
      Member member, Book.BookStatus bookStatus, Pageable pageable);

  //AND CREATED_AT > ?2 AND CREATED_AT < ?3
  @Query(
      value =
          "SELECT * FROM book WHERE MEMBER_ID = ?1 AND BOOK_STATUS = 0 AND NOW() >"
              + " DATE_ADD(CREATED_AT, INTERVAL +3 MONTH)",
      countQuery =
          "SELECT count(*) FROM book WHERE MEMBER_ID = ?1 AND BOOK_STATUS = 0 AND NOW() >"
              + " DATE_ADD(CREATED_AT, INTERVAL +3 MONTH)",
      nativeQuery = true)
  Page<Book> findAbandon(Member member, Pageable pageable);

  @Query(
      value = "SELECT * FROM book WHERE MEMBER_ID = ?1 AND MEMO_COUNT != 0",
      countQuery = "SELECT count(*) FROM book WHERE MEMBER_ID = ?1 AND MEMO_COUNT != 0",
      nativeQuery = true)
  Page<Book> findBooksHaveMemo(Member member, Pageable pageable);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE Book b SET b.createdAt = :time WHERE b.bookId = :bookId")
  void modifyCreateDate(LocalDateTime time, long bookId);
}
