package com.seollem.server.memolike;

import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.MemoService;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.util.Map;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/memo-like")
@RequiredArgsConstructor
@Validated
public class MemoLikeController {

  private final MemoLikeService memoLikeService;
  private final GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  private final MemberService memberService;
  private final MemoService memoService;


  @PostMapping(path = "/{memo-id}")
  public ResponseEntity doLike(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @PathVariable("memo-id") long memoId) {

    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    Memo memo = memoService.findVerifiedMemo(memoId);

    MemoLike savedMemoLike = memoLikeService.createMemoLike(memo, member);

    MemoLikeResponseDto responseDto = new MemoLikeResponseDto(savedMemoLike.getMemoLikeId());

    return new ResponseEntity(responseDto, HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{memo-like-id}")
  public ResponseEntity cancelLike(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @PathVariable("memo-like-id") long memoLikeId) {

    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    MemoLike memoLike = memoLikeService.findVerifiedMemoLikeById(memoLikeId);
    memoLikeService.verifyMemberHasMemoLike(member, memoLike);
    memoLikeService.deleteMemoLikeWithMemoLike(memoLike);

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
