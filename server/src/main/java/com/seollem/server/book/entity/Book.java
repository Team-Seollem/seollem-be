package com.seollem.server.book.entity;

import com.seollem.server.audit.Auditable;
import com.seollem.server.member.entity.Member;
import net.bytebuddy.asm.Advice;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    @Column(nullable = false, unique = true)
    private String title;
    private String cover;
    private String authors;
    private String publisher;
    private int currentPage;
    private int itemPage;
    private int star;
    private int bookCount;
    private String bookStatus;
    private LocalDateTime readStartDate;
    private LocalDateTime readEndDate;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
