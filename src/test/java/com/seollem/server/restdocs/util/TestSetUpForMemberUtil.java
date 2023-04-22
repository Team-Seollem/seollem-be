package com.seollem.server.restdocs.util;

import com.seollem.server.file.FileUploadService;
import com.seollem.server.member.MemberMapper;
import com.seollem.server.member.MemberService;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.springframework.boot.test.mock.mockito.MockBean;

public class TestSetUpForMemberUtil extends WebMvcTestSetUpUtil {

  @MockBean
  protected MemberMapper memberMapper;
  @MockBean
  protected MemberService memberService;
  @MockBean
  protected GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  @MockBean
  protected FileUploadService fileUploadService;

}
