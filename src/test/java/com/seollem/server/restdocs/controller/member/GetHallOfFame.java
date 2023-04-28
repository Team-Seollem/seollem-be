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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(MemberController.class)
public class GetHallOfFame extends TestSetUpForMemberUtil {


  @Test
  public void getHallOfFameTest() throws Exception {
    //given
    when(memberService.getHallOfFameWithBook()).thenReturn(
        StubDataUtil.MockMember.getHallOfFameWithBook());
    when(memberService.getHallOfFameWithMemo()).thenReturn(
        StubDataUtil.MockMember.getHallOfFameWithMemo());

    //when
    ResultActions resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/members/hall-of-fame")
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .header("Authorization",
                "Bearer JWT Access Token"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("GetHallOfFame",
        requestHeaders(
            headerWithName("Authorization").description("Bearer JWT Access Token")),
        responseFields(
            fieldWithPath("mostReadMember[].memberId").type(JsonFieldType.NUMBER)
                .description("회원 ID"),
            fieldWithPath("mostReadMember[].url").type(JsonFieldType.STRING)
                .description("회원 이미지 URL"),
            fieldWithPath("mostReadMember[].name").type(JsonFieldType.STRING).description("회원 이름"),
            fieldWithPath("mostReadMember[].count").type(JsonFieldType.NUMBER)
                .description("회원이 등록한 책 개수"),

            fieldWithPath("mostMemoedMember[].memberId").type(JsonFieldType.NUMBER)
                .description("회원 ID"),
            fieldWithPath("mostMemoedMember[].url").type(JsonFieldType.STRING)
                .description("회원 이미지 URL"),
            fieldWithPath("mostMemoedMember[].name").type(JsonFieldType.STRING)
                .description("회원 이름"),
            fieldWithPath("mostMemoedMember[].count").type(JsonFieldType.NUMBER)
                .description("회원이 등록한 메모 개수")

        )));
  }
}