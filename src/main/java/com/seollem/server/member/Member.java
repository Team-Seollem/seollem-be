package com.seollem.server.member;

import com.seollem.server.audit.Auditable;
import com.seollem.server.book.Book;
import com.seollem.server.memolike.MemoLike;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long memberId;

  @Column(nullable = false, unique = true)
  private String email;

  private String name;

  @Column(nullable = false)
  private String password;
  private String roles;
  private String content;
  private String url;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<Book> books = new ArrayList<>();


  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<MemoLike> memoLikes;

  public List<String> getRoleList() {
    if (this.roles.length() > 0) {
      return Arrays.asList(this.roles.split(","));
    }
    return new ArrayList<>();
  }
}
