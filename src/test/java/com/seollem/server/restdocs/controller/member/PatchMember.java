package com.seollem.server.restdocs.controller.member;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberController;
import com.seollem.server.member.MemberDto;
import com.seollem.server.member.MemberDto.Patch;
import com.seollem.server.member.MemberMapper;
import com.seollem.server.member.MemberService;
import com.seollem.server.restdocs.util.GsonCustomConfig;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(MemberController.class)
public class PatchMember extends WebMvcTestSetUpUtil {

  private final GsonCustomConfig gsonCustomConfig = new GsonCustomConfig();
  @MockBean
  private MemberMapper memberMapper;
  @MockBean
  private MemberService memberService;
  @MockBean
  private GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;

  @Test
  public void patchMemberTest() throws Exception {
    Gson gson = gsonCustomConfig.gsonBuild();

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "nobody@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(memberMapper.memberPatchToMember(Mockito.any())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(memberService.updateMember(Mockito.any())).thenReturn(new Member());

    when(memberMapper.memberToMemberPatchResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockMember.getMemberPatchResponse());

    MemberDto.Patch patch = new Patch("이슬", "modi!@#pas1");
    String content = gson.toJson(patch);

    //when, then
    this.mockMvc.perform(
            patch("/members/me").content(content).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .header("Authorization", "Bearer JWT Access Token"))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("PatchMember",
            requestHeaders(
                headerWithName("Authorization").description("Bearer JWT Access Token")
            ),
            requestFields(
                fieldWithPath("name").description("변경할 이름"),
                fieldWithPath("password").description("변경할 비밀번호 : 알파벳, 숫자, 특수문자 포함 6자 이상이어야 합니다."),
                fieldWithPath("url").description("이미지"),
                fieldWithPath("profile").description("프로필")

            ),
            responseFields(
                fieldWithPath("email").description("이메일"),
                fieldWithPath("name").description("변경된 이름"),
                fieldWithPath("updatedAt").description("변경된 일자"),
                fieldWithPath("url").description("이미지"),
                fieldWithPath("profile").description("프로필")
            )
        ));

  }

}
