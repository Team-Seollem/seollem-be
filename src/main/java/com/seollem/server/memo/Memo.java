package com.seollem.server.memo;

import com.seollem.server.audit.Auditable;
import com.seollem.server.book.Book;
import com.seollem.server.member.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

  public void setBook(Book book) {
    this.book = book;
  }

  public enum MemoType {
    BOOK_CONTENT(1, "book_content"),
    SUMMARY(2, "summary"),
    THOUGHT(3, "thought"),
    QUESTION(4, "question"),
    ALL(5, "all");

    @Getter
    private final int stepNumber;
    @Getter
    private final String stepDescription;

    MemoType(int stepNumber, String stepDescription) {
      this.stepNumber = stepNumber;
      this.stepDescription = stepDescription;
    }
  }
}
