package com.seollem.server.memo;

import com.seollem.server.book.entity.Book;
import com.seollem.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    Optional<Memo> findById(long memoId);

    List<Memo> findALLByBook(Book book);

    Page<Memo> findAllByBookAndMemoType(Pageable pageable, Book book, Memo.MemoType memoType);

    Page<Memo> findAllByBook(Pageable pageable, Book book);


    @Query(value = "SELECT * FROM memo WHERE MEMBER_ID = ?1 order by RAND() limit 1", nativeQuery = true)
    List<Memo> findAllByMember(Member member);


}

