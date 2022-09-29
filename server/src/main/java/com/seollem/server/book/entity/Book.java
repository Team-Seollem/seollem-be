package com.seollem.server.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seollem.server.audit.Auditable;
import com.seollem.server.member.entity.Member;
import com.seollem.server.memo.Memo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name= "BOOK")
@Setter
@Getter
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    @Column(nullable = false, unique = true)
    private String title;
    private String cover;
    private String author;
    private String publisher;
    private int currentPage;
    private int itemPage;
    private int star;
    private BookStatus bookStatus;
    private LocalDateTime readStartDate;
    private LocalDateTime readEndDate;
    private int memoCount;

    @Getter
    public enum BookStatus{
        YET, ING, DONE

    }

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"book"})
    private List<Memo> memos = new ArrayList<>();

    public void addMemo(Memo memo) {
        this.memos.add(memo);
        if (memo.getBook() != this) {
            memo.setBook(this);
        }
    }
}
