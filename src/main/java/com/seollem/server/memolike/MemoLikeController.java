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

    memoLikeService.createLike(memo, member);

    return new ResponseEntity(HttpStatus.CREATED);
  }
}
