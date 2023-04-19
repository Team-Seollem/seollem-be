package com.seollem.server.memo;

import com.seollem.server.audit.Auditable;
import com.seollem.server.book.Book;
import com.seollem.server.member.Member;
import com.seollem.server.memolikes.MemoLikes;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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

  private int memoLikesCount;

  @Enumerated(EnumType.STRING)
  private MemoAuthority memoAuthority;

  @ManyToOne
  @JoinColumn(name = "BOOK_ID")
  private Book book;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL)
  private List<MemoLikes> memoLikes;

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
    private final int memoTypeNumber;
    @Getter
    private final String memoTypeDescription;

    MemoType(int memoTypeNumber, String memoTypeDescription) {
      this.memoTypeNumber = memoTypeNumber;
      this.memoTypeDescription = memoTypeDescription;
    }
  }

  public enum MemoAuthority {
    PUBLIC(0),
    PRIVATE(1),
    ALL(2);

    @Getter
    private final int memoAuthorityNumber;

    MemoAuthority(int memoAuthorityNumber) {
      this.memoAuthorityNumber = memoAuthorityNumber;
    }
  }

  @PrePersist
  public void prePersist() {
    this.memoType = this.memoType == null ? MemoType.BOOK_CONTENT : this.memoType;
    this.memoAuthority = this.memoAuthority == null ? MemoAuthority.PRIVATE : this.memoAuthority;

  }
}
