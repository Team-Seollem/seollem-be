package com.seollem.server.restdocs.util;

import com.seollem.server.book.BookMapper;
import com.seollem.server.book.BookService;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.util.GetAbandonPeriodUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.springframework.boot.test.mock.mockito.MockBean;

public class TestSetUpForBookUtil extends WebMvcTestSetUpUtil {

  @MockBean
  protected GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  @MockBean
  protected MemberService memberService;
  @MockBean
  protected BookService bookService;
  @MockBean
  protected BookMapper bookMapper;
  @MockBean
  protected MemoService memoService;
  @MockBean
  protected MemoMapper memoMapper;
  @MockBean
  protected MemoLikesService memoLikesService;
  @MockBean
  protected GetAbandonPeriodUtil getAbandonPeriodUtil;

}
