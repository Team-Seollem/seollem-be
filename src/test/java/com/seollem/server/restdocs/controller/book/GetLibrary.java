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
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookController;
import com.seollem.server.member.Member;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForBookUtil;
import java.util.List;
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

/**
 * 작성자 : 김형섭 수정일 : 2023.03.21 개선사항 [] 1. 다른 컨트롤러 테스트에서도 사용될만한 Mock 객체들을 모듈화 [] 2. 필드의 required 여부,
 * api 문서를 더 이쁘게 꾸미는 여러 방법 고려
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(BookController.class)
public class GetLibrary extends TestSetUpForBookUtil {


  @Autowired
  private Gson gson;

  @Test
  public void libraryTest() throws Exception {
    //given
    given(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).willReturn(
        "starrypro@gmail.com");

    given(memberService.findVerifiedMemberByEmail(Mockito.anyString())).willReturn(
        StubDataUtil.MockMember.getMember());

    given(bookService.findVerifiedBooksByMemberAndBookStatus(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.any(Member.class), Mockito.any(BookStatus.class),
        Mockito.anyString())).willReturn(StubDataUtil.MockBook.getBookPage());

    given(bookMapper.BooksToLibraryResponse(Mockito.any())).willReturn(
        StubDataUtil.MockBook.getLibraryResponse());

    //when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/books/library").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).header("Authorization",
                "Bearer JWT Access Token")
            .queryParam("page", "1").queryParam("size", "10").queryParam("bookStatus", "YET"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("Library",
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        requestParameters(
            List.of(parameterWithName("page").description("원하는 page"),
                parameterWithName("size").description("페이지 별 책 개수"),
                parameterWithName("bookStatus").description("BookStatus : YET, ING, DONE"))),
        responseFields(
            fieldWithPath("item[].bookId").type(JsonFieldType.NUMBER).description("Book-id"),
            fieldWithPath("item[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("item[].cover").type(JsonFieldType.STRING).description("표지 이미지 url"),
            fieldWithPath("item[].author").type(JsonFieldType.STRING).description("저자"),
            fieldWithPath("item[].createdAt").type(JsonFieldType.STRING).description("생성 시간"),
            fieldWithPath("item[].star").type(JsonFieldType.NUMBER).description("별점"),
            fieldWithPath("item[].currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
            fieldWithPath("item[].itemPage").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
            fieldWithPath("item[].bookStatus").type(JsonFieldType.STRING)
                .description("읽기 상태 : 읽기 전, 읽는 중, 읽기 완료"),
            fieldWithPath("item[].memoCount").type(JsonFieldType.NUMBER)
                .description("책에 달린 메모의 개수"),
            fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("조회 페이지"),
            fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지별 책 개수"),
            fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER)
                .description("조회된 책의 총 개수"),
            fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER)
                .description("조회된 총 페이지 수")

        )));
  }
}

