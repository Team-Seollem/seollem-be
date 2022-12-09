package com.seollem.server.book.repository;

import com.seollem.server.book.entity.Book;
import com.seollem.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(long bookId);
    @Query(value = "SELECT b FROM Book b WHERE b.title = :title AND b.member.memberId = :memberId")
    Optional<Book> findByTitle(String title, long memberId);
//    Optional<List<Book>>  findByMember(Member member);
    Page<Book> findAllByMemberAndBookStatus(Pageable pageable, Member member, Book.BookStatus bookStatus);

    @Query(value = "SELECT * FROM book WHERE MEMBER_ID = ?1 AND BOOK_STATUS = 0 AND NOW() > DATE_ADD(CREATED_AT, INTERVAL +3 MONTH)",
            countQuery = "SELECT count(*) FROM book WHERE MEMBER_ID = ?1 AND BOOK_STATUS = 0 AND NOW() > DATE_ADD(CREATED_AT, INTERVAL +3 MONTH)",
            nativeQuery = true)
    Page<Book> findAbandon(Member member, Pageable pageable);

    @Query(value = "SELECT * FROM book WHERE MEMBER_ID = ?1 AND MEMO_COUNT != 0",
            countQuery = "SELECT count(*) FROM book WHERE MEMBER_ID = ?1 AND MEMO_COUNT != 0",
            nativeQuery = true)
    Page<Book> findBooksHaveMemo(Member member, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Book b SET b.createdAt = :time WHERE b.bookId = :bookId")
    void modifyCreateDate(LocalDateTime time, long bookId);


}
