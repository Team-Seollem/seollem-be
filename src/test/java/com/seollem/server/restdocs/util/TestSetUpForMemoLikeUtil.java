package com.seollem.server.restdocs.util;

import com.seollem.server.member.MemberService;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolike.MemoLikeService;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.springframework.boot.test.mock.mockito.MockBean;

public class TestSetUpForMemoLikeUtil extends WebMvcTestSetUpUtil {

  @MockBean
  protected MemoLikeService memoLikeService;
  @MockBean
  protected GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  @MockBean
  protected MemberService memberService;
  @MockBean
  protected MemoService memoService;

}
