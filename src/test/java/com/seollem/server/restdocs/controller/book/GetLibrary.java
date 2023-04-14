package com.seollem.server.restdocs.controller.book;

import static org.mockito.Mockito.when;
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
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForBookUtil;
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
public class GetLibrary extends TestSetUpForBookUtil {


  @Autowired
  private Gson gson;

  @Test
  public void libraryTest() throws Exception {
    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(bookService.findVerifiedBooksByMemberAndBookStatus(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.any(), Mockito.any(), Mockito.anyString())).thenReturn(
        StubDataUtil.MockBook.getBookPage());

    when(bookMapper.BooksToLibraryResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockBook.getLibraryResponse());

    //when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/books/library").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer JWT Access Token").queryParam("page", "1")
            .queryParam("size", "2").queryParam("bookStatus", "YET"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("Library",
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        requestParameters(parameterWithName("page").description("원하는 page"),
            parameterWithName("size").description("페이지 별 책 개수"),
            parameterWithName("bookStatus").description(
                "책 읽기 상태 : YET(읽기 전), ING(읽는 중), DONE(읽기 완료)")), responseFields(
            fieldWithPath("item[].bookId").type(JsonFieldType.NUMBER).description("책 ID"),
            fieldWithPath("item[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("item[].cover").type(JsonFieldType.STRING).description("표지 이미지 url"),
            fieldWithPath("item[].author").type(JsonFieldType.STRING).description("저자"),
            fieldWithPath("item[].createdAt").type(JsonFieldType.STRING).description("등록 일자"),
            fieldWithPath("item[].star").type(JsonFieldType.NUMBER).description("별점"),
            fieldWithPath("item[].currentPage").type(JsonFieldType.NUMBER)
                .description("현재 읽고 있는 페이지"),
            fieldWithPath("item[].itemPage").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
            fieldWithPath("item[].bookStatus").type(JsonFieldType.STRING)
                .description("책 읽기 상태 : YET(읽기 전), ING(읽는 중), DONE(읽기 완료)"),
            fieldWithPath("item[].memoCount").type(JsonFieldType.NUMBER)
                .description("책에 달린 메모의 총 개수"),
            fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("조회 페이지"),
            fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지별 책 개수"),
            fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER)
                .description("조회된 책의 총 개수"),
            fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER)
                .description("조회된 총 페이지 수")

        )));
  }
}

