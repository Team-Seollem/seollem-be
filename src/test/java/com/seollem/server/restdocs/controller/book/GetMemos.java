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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.book.BookController;
import com.seollem.server.book.BookMapper;
import com.seollem.server.book.BookService;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(BookController.class)
public class GetMemos extends WebMvcTestSetUpUtil {

  @MockBean
  private GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  @MockBean
  private MemberService memberService;
  @MockBean
  private BookService bookService;
  @MockBean
  private BookMapper bookMapper;
  @MockBean
  private MemoService memoService;
  @MockBean
  private MemoMapper memoMapper;
  @MockBean
  private MemoLikesService memoLikesService;

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

    given(memoService.getBookAndMemo(Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).willReturn(
        StubDataUtil.MockMemo.getMemoPage());

    given(memoService.getBookAndMemoTypes(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(),
        Mockito.any())).willReturn(StubDataUtil.MockMemo.getMemoPage());

    given(memoLikesService.getMemoLikesCountWithMemo(Mockito.any())).willReturn(3);

    given(memoMapper.memoToMemoResponses(Mockito.any())).willReturn(
        StubDataUtil.MockMemo.getMemoResponses());

    //when
    ResultActions resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/books/{book-id}/memos", 1)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .header("Authorization",
                "Bearer JWT Access Token")
            .queryParam("page", "1")
            .queryParam("size", "10")
            .queryParam("memoType", "BOOK_CONTENT"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("GetMemos",
        requestHeaders(
            headerWithName("Authorization").description("Bearer JWT Access Token")),
        pathParameters(
            parameterWithName("book-id").description("조회할 책 ID")),
        requestParameters(
            parameterWithName("page").description("원하는 페이지"),
            parameterWithName("size").description("페이지 별 책 개수"),
            parameterWithName("memoType").description(
                "메모 타입 : BOOK_CONTENT, SUMMARY, THOUGHT, QUESTION, ALL")),
        responseFields(
            fieldWithPath("item[].memoId").type(JsonFieldType.NUMBER).description("Memo-id"),
            fieldWithPath("item[].memoType").type(JsonFieldType.STRING).description(
                "메모 타입 : 전체(ALL), 책 속 문장(BOOK_CONTENT), 책 내용 요약(SUMMARY), 나만의 생각(THOUGHT), 나만의 질문(QUESTION)"),
            fieldWithPath("item[].memoContent").type(JsonFieldType.STRING)
                .description("메모 내용"),
            fieldWithPath("item[].memoBookPage").type(JsonFieldType.NUMBER)
                .description("메모와 연관된 책의 페이지"),
            fieldWithPath("item[].memoAuthority").type(JsonFieldType.STRING)
                .description("메모 보기 권한 : PUBLIC, PRIVATE"),
            fieldWithPath("item[].memoLikesCount").type(JsonFieldType.NUMBER)
                .description("메모에 달린 좋아요 수"),
            fieldWithPath("item[].createdAt").type(JsonFieldType.STRING)
                .description("메모 생성 일자"),
            fieldWithPath("item[].updatedAt").type(JsonFieldType.STRING)
                .description("메모 수정 일자"),
            fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("조회 페이지"),
            fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지별 책 개수"),
            fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER)
                .description("조회된 책의 총 개수"),
            fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER)
                .description("조회된 총 페이지 수")

        )));
  }
}