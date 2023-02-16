package com.seollem.server.memo;

import com.seollem.server.audit.Auditable;
import com.seollem.server.member.Member;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class MemoLikes extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int memoLikesId;

  @ManyToOne
  @JoinColumn(name = "MEMO_ID")
  private Memo memo;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

}
