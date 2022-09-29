package com.seollem.server.book.repository;

import com.seollem.server.book.entity.Book;
import com.seollem.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(long bookId);
    Optional<Book> findByTitle(String title);
//    Optional<List<Book>>  findByMember(Member member);
    Page<Book> findAllByMemberAndBookStatus(Pageable pageable, Member member, Book.BookStatus bookStatus);

    @Query(value = "SELECT * FROM BOOK WHERE MEMBER_ID = ?1 AND BOOK_STATUS = 0 AND NOW() > DATE_ADD(CREATED_AT, INTERVAL +3 MONTH)",
            countQuery = "SELECT count(*) FROM BOOK WHERE MEMBER_ID = ?1 AND BOOK_STATUS = 0 AND NOW() > DATE_ADD(CREATED_AT, INTERVAL +3 MONTH)",
            nativeQuery = true)
    Page<Book> findAllByMember(Member member, Pageable pageable);


}
