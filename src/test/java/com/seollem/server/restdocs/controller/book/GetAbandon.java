package com.seollem.server.restdocs.controller.book;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.book.BookController;
import com.seollem.server.book.BookMapper;
import com.seollem.server.book.BookService;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.util.List;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(BookController.class)
public class GetAbandon extends WebMvcTestSetUpUtil {

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
  public void abandonTest() throws Exception {
    //given
    given(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).willReturn(
        "starrypro@gmail.com");

    given(memberService.findVerifiedMemberByEmail(Mockito.anyString())).willReturn(
        StubDataUtil.MockMember.getMember());

    given(bookService.findAbandonedBooks(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.any(Member.class))).willReturn(
        StubDataUtil.MockBook.getBookPage());

    given(bookMapper.BooksToAbandonResponse(Mockito.any())).willReturn(
        StubDataUtil.MockBook.getAbandonResponse());

    //when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/books/abandon").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).header("Authorization",
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZW1haWwiOiJzdGFycnlwcm9AZ21haWwuY29tIiwic3ViIjoic3RhcnJ5cHJvQGdtYWlsLmNvbSIsImlhdCI6MTY3OTQ2MTg0NSwiZXhwIjoxNjc5NDY3ODQ1fQ.ri2B7x4vhgydiJYdXtTTZuD9EZzX-l4coGwiPWYjYDUeWLSunsBDkdslQdzZb9D72qIlArzzP7nCalROkVYOTA")
            .queryParam("page", "1").queryParam("size", "10"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("Abandon",
        preprocessRequest(modifyUris().scheme(SCHEMA).host(URI).removePort(), prettyPrint()),
        preprocessResponse(prettyPrint()),
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        requestParameters(
            List.of(parameterWithName("page").description("원하는 page"),
                parameterWithName("size").description("페이지 별 책 개수"))),
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