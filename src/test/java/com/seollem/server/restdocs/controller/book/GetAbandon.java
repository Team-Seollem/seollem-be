package com.seollem.server.restdocs.controller.book;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.book.BookController;
import com.seollem.server.member.Member;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForBookUtil;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(BookController.class)
public class GetAbandon extends TestSetUpForBookUtil {

  @Autowired
  private Gson gson;

  @Test
  public void abandonTest() throws Exception {
    //given
    given(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).willReturn(
        "starrypro@gmail.com");

    given(memberService.findVerifiedMemberByEmail(Mockito.anyString())).willReturn(
        StubDataUtil.MockMember.getMember());

    given(getAbandonPeriodUtil.getAbandonPeriod(Mockito.anyInt(), Mockito.anyInt())).willReturn(
        new ArrayList<>());

    given(bookService.findAbandonedBooks(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.any(Member.class), Mockito.any())).willReturn(
        StubDataUtil.MockBook.getBookPage());

    given(bookMapper.BooksToAbandonResponse(Mockito.any())).willReturn(
        StubDataUtil.MockBook.getAbandonResponse());

    //when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/books/abandon").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).header("Authorization",
                "Bearer JWT Access Token")
            .queryParam("page", "1").queryParam("size", "10")
            .queryParam("year", "2023").queryParam("month", "9"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("Abandon",
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        requestParameters(
            parameterWithName("page").description("원하는 page"),
            parameterWithName("size").description("페이지 별 책 개수"),
            parameterWithName("year").description("검색할 연도"),
            parameterWithName("month").description("검색할 월")),
        responseFields(
            fieldWithPath("item[].bookId").type(JsonFieldType.NUMBER).description("Book-id"),
            fieldWithPath("item[].createdAt").type(JsonFieldType.STRING).description("책 등록 일자"),
            fieldWithPath("item[].title").type(JsonFieldType.STRING).description("책 제목"),
            fieldWithPath("item[].cover").type(JsonFieldType.STRING).description("표지 이미지 url"),
            fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("조회 페이지"),
            fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지별 책 개수"),
            fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER)
                .description("조회된 책의 총 개수"),
            fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER)
                .description("조회된 총 페이지 수")
        )));
  }
}