package com.seollem.server.member.dto.othermemberbook;

import com.seollem.server.memo.Memo.MemoType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OtherMemberBookMemoDto {

  private long memoId;
  private MemoType memoType;
  private String memoContent;
  private int memoBookPage;
  private int memoLikesCount;
  private boolean memoLikeDone;


  public OtherMemberBookMemoDto(long memoId, MemoType memoType, String memoContent,
      int memoBookPage) {
    this.memoId = memoId;
    this.memoType = memoType;
    this.memoContent = memoContent;
    this.memoBookPage = memoBookPage;
  }

}
