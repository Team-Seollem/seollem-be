package com.seollem.server.restdocs.controller.member;


import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seollem.server.member.MemberController;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForMemberUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(MemberController.class)
public class GetMember extends TestSetUpForMemberUtil {


  @Test
  public void getMemberTest() throws Exception {
    //given
    when(memberMapper.memberToMemberGetResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockMember.getMemberGetResponse());

    //when
    ResultActions resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/members/me")
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .header("Authorization",
                "Bearer JWT Access Token"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("GetMember",
        requestHeaders(
            headerWithName("Authorization").description("Bearer JWT Access Token")),
        responseFields(
            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("자기 소개"),
            fieldWithPath("url").type(JsonFieldType.STRING).description("이미지")

        )));
  }
}