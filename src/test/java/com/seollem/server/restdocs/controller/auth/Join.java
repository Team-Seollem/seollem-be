package com.seollem.server.restdocs.controller.auth;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.auth.controller.AuthController;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberMapper;
import com.seollem.server.member.MemberService;
import com.seollem.server.member.dto.MemberDto;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(AuthController.class)
public class Join extends WebMvcTestSetUpUtil {

  @MockBean
  private MemberService memberService;

  @MockBean
  private MemberMapper memberMapper;

  @Autowired
  private Gson gson;

  @Test
  public void authJoinTest() throws Exception {
    //given
    when(memberMapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).thenReturn(
        new Member());

    when(memberService.createMember(Mockito.any(Member.class), anyString())).thenReturn(
        new Member());

    MemberDto.Post post = new MemberDto.Post("khs@gmail.com", "김형섭", "password123!", "123456");
    String content = gson.toJson(post);

    //when, then
    mockMvc.perform(MockMvcRequestBuilders.post("/join").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated())
        .andDo(document("Join",
            requestFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                fieldWithPath("authenticationCode").type(JsonFieldType.STRING)
                    .description("인증번호"))));
  }
}