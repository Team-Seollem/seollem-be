package com.seollem.server.book;

import com.seollem.server.audit.Auditable;
import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  //    @JsonIgnoreProperties({"book"})
  private List<Memo> memos = new ArrayList<>();

  public void addMemo(Memo memo) {
    this.memos.add(memo);
    if (memo.getBook() != this) {
      memo.setBook(this);
    }
  }

  @Getter
  public enum BookStatus {
    YET,
    ING,
    DONE
  }
}
