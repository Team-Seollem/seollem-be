package com.seollem.server.book.entity;

import com.seollem.server.audit.Auditable;
import com.seollem.server.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Getter
    public enum BookStatus{
        YET, ING, DONE

    }

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
