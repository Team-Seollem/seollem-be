package com.seollem.server.book.repository;

import com.seollem.server.book.entity.Book;
import com.seollem.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(long bookId);
    Optional<Book> findByTitle(String title);
//    Optional<List<Book>>  findByMember(Member member);
    Page<Book> findAllByMember(Pageable pageable, Member member);

    long countByTitle(String title);
}
