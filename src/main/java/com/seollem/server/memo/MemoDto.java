package com.seollem.server.memo;

import com.seollem.server.memo.Memo.MemoAuthority;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemoDto {

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Post {

    @NotBlank(message = "메모는 공백이 아니어야 합니다.")
    private String memoContent;

    private int memoBookPage;

    private Memo.MemoType memoType;

    private MemoAuthority memoAuthority;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Patch {

    private long memoId;

    @NotBlank(message = "메모는 공백이 아니어야 합니다.")
    private String memoContent;

    private Memo.MemoType memoType;

    private int memoBookPage;

    private MemoAuthority memoAuthority;

  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class RandomResponse {

    private long memoId;
    private String memoContent;
    private Memo.MemoType memoType;
    private int memoBookPage;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Response {

    private long memoId;
    private Memo.MemoType memoType;
    private String memoContent;
    private int memoBookPage;
    private MemoAuthority memoAuthority;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
