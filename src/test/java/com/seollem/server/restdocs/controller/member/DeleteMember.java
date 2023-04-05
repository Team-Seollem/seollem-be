package com.seollem.server.restdocs.controller.member;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seollem.server.member.MemberController;
import com.seollem.server.member.MemberMapper;
import com.seollem.server.member.MemberService;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(value = MemberController.class)
public class DeleteMember extends WebMvcTestSetUpUtil {

  @MockBean
  private MemberMapper memberMapper;
  @MockBean
  private MemberService memberService;
  @MockBean
  private GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;


  @Test
  public void deleteMemberTest() throws Exception {
    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "nobody@gmail.com");

    doNothing().when(memberService).deleteMember(Mockito.anyString());

    //when, then
    this.mockMvc.perform(delete("/members/me")
            .header("Authorization", "Bearer JWT Access Token"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andDo(document("DeleteMember",
            requestHeaders(
                headerWithName("Authorization").description("Bearer JWT Access Token")
            )
        ));
  }
}
