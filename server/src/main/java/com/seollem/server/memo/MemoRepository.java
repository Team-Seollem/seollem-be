package com.seollem.server.memo;

import com.seollem.server.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    Optional<Memo> findById(long memoId);

    List<Memo> findALLByBook(Book book);

    List<Memo> findAllByBookAndMemoType(Book book, Memo.MemoType memoType);

    @Query(value = "SELECT * FROM MEMO order by RAND() limit 1", nativeQuery = true)
    List<Memo> findAll();

//    @Query(value = "SELECT memoId FROM MEMO order by RAND() limit 1",nativeQuery = true)
//    Page<Memo> findByMemoIdAndContent(long memoId, String content, Pageable pageable);

}

