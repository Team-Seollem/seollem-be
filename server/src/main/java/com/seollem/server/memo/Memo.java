package com.seollem.server.memo;


import com.seollem.server.audit.Auditable;
import com.seollem.server.book.entity.Book;
import com.seollem.server.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "MEMO")
public class Memo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memoId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemoType memoType;
    @Column(columnDefinition = "Text")
    private String memoContent;
    private int memoBookPage;


    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void setBook(Book book){this.book = book;}

    public enum MemoType {
        BOOK_CONTENT(1, "book_content"),
        SUMMARY(2, "summary"),
        THOUGHT(3, "thought"),
        QUESTION(4, "question"),
        All(5,"all");


        @Getter
        private int stepNumber;
        @Getter
        private String stepDescription;

        MemoType(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }

}


