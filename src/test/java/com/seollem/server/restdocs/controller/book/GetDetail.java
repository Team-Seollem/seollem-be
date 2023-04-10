package com.seollem.server.restdocs.controller.book;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(BookController.class)
public class GetDetail extends TestSetUpForBookUtil {

  @Autowired
  private Gson gson;

  @Test
  public void DetailTest() throws Exception {
    //given
    given(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).willReturn(
        "starrypro@gmail.com");

    given(memberService.findVerifiedMemberByEmail(Mockito.anyString())).willReturn(
        StubDataUtil.MockMember.getMember());

    doNothing().when(bookService).verifyMemberHasBook(Mockito.anyLong(), Mockito.anyLong());

    given(bookService.findVerifiedBookById(Mockito.anyLong())).willReturn(
        StubDataUtil.MockBook.getBook());

    given(bookMapper.BookToBookDetailResponse(Mockito.any())).willReturn(
        StubDataUtil.MockBook.getBookDetailResponse());

    given(memoService.getMemos(Mockito.any())).willReturn(StubDataUtil.MockMemo.getMemos());

    given(memoService.getMemoWithAuthority(Mockito.any())).willReturn(
        StubDataUtil.MockMemo.getMemos());

    given(memoLikesService.getMemoLikesCountWithMemo(Mockito.any())).willReturn(3);

    given(memoMapper.memoToMemoResponses(Mockito.any())).willReturn(
        StubDataUtil.MockMemo.getMemoResponses());

    //when
    ResultActions resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/books/{book-id}", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).header("Authorization",
                "Bearer JWT Access Token")
            .queryParam("memoAuthority", "PUBLIC"));

    //then
    resultActions.andExpect(status().isOk()).andDo(print()).andDo(document("BookDetail",
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        pathParameters(parameterWithName("book-id").description("조회할 책 ID")),
        requestParameters(
            parameterWithName("memoAuthority").description("메모의 보기 권한 : PUBLIC, PRIVATE, ALL")),
        responseFields(fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("Book-id"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("책 제목"),
            fieldWithPath("cover").type(JsonFieldType.STRING).description("표지 이미지 URL"),
            fieldWithPath("author").type(JsonFieldType.STRING).description("저자"),
            fieldWithPath("publisher").type(JsonFieldType.STRING).description("출판사"),
            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("등록 일자"),
            fieldWithPath("star").type(JsonFieldType.NUMBER).description("별점"),
            fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 읽고 있는 페이지"),
            fieldWithPath("itemPage").type(JsonFieldType.NUMBER).description("책 전체 페이지 수"),
            fieldWithPath("bookStatus").type(JsonFieldType.STRING)
                .description("책 읽기 상태 : 읽기 전(YET), 읽는 중(ING), 읽기 완료(DONE)"),
            fieldWithPath("readStartDate").type(JsonFieldType.STRING).description("읽기 시작한 일자"),
            fieldWithPath("readEndDate").type(JsonFieldType.STRING).description("읽기 완료한 일자"),
            fieldWithPath("memosList[].memoId").type(JsonFieldType.NUMBER).description("Memo-id"),
            fieldWithPath("memosList[].memoType").type(JsonFieldType.STRING).description(
                "메모 타입 : 전체(ALL), 책 속 문장(BOOK_CONTENT), 책 내용 요약(SUMMARY), 나만의 생각(THOUGHT), 나만의 질문(QUESTION)"),
            fieldWithPath("memosList[].memoContent").type(JsonFieldType.STRING)
                .description("메모 내용"),
            fieldWithPath("memosList[].memoBookPage").type(JsonFieldType.NUMBER)
                .description("메모와 연관된 책의 페이지"),
            fieldWithPath("memosList[].memoAuthority").type(JsonFieldType.STRING)
                .description("메모 보기 권한 : PUBLIC, PRIVATE"),
            fieldWithPath("memosList[].memoLikesCount").type(JsonFieldType.NUMBER)
                .description("메모에 달린 좋아요 수"),
            fieldWithPath("memosList[].createdAt").type(JsonFieldType.STRING)
                .description("메모 생성 일자"),
            fieldWithPath("memosList[].updatedAt").type(JsonFieldType.STRING)
                .description("메모 수정 일자"),
            fieldWithPath("memoCount").type(JsonFieldType.NUMBER).description("책에 달린 메모의 총 개수")

        )));
  }
}