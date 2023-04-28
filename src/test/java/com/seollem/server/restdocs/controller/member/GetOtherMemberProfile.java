package com.seollem.server.restdocs.controller.member;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
public class GetOtherMemberProfile extends TestSetUpForMemberUtil {


  @Test
  public void getOtherMemberProfileTest() throws Exception {
    //given
    when(memberService.getOtherMemberProfile(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.any())).thenReturn(StubDataUtil.MockMember.getOtherMemberProfile());

    //when
    ResultActions resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/members/other/{member-id}", 1)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer JWT Access Token").queryParam("page", "1")
            .queryParam("size", "2"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("GetOtherMemberProfile",
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        pathParameters(parameterWithName("member-id").description("조회할 회원 ID")),
        requestParameters(parameterWithName("page").description("원하는 otherLibrary의 page"),
            parameterWithName("size").description("페이지 별 책 개수")),
        responseFields(fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
            fieldWithPath("url").type(JsonFieldType.STRING).description("회원 이미지 URL"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("회원의 자기소개 내용"),
            fieldWithPath("otherLibrary[].bookId").type(JsonFieldType.NUMBER).description("책 ID"),
            fieldWithPath("otherLibrary[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("otherLibrary[].author").type(JsonFieldType.STRING).description("저자"),
            fieldWithPath("otherLibrary[].cover").type(JsonFieldType.STRING)
                .description("책 이미지 URL"),
            fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER)
                .description("조회한 otherLibrary 페이지"),
            fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지별 책 개수"),
            fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER)
                .description("조회된 책의 총 개수"),
            fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER)
                .description("조회된 총 페이지 수")

        )));
  }
}