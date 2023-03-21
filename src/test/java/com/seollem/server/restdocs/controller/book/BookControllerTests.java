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
import com.seollem.server.book.Book;
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookController;
import com.seollem.server.book.BookDto;
import com.seollem.server.book.BookDto.LibraryResponse;
import com.seollem.server.book.BookMapper;
import com.seollem.server.book.BookService;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.restdocs.util.SetUpUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class BookControllerTests extends SetUpUtil {

  @Nested
  @ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
  @WebMvcTest(BookController.class)
  class Library {

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
    public void libraryTest() throws Exception {
      //given
      given(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).willReturn(
          "abcd@gmail.com");

      given(memberService.findVerifiedMemberByEmail(Mockito.anyString())).willReturn(new Member());

      Book book = new Book(1, "title", "cover", "author", "publisher", 1, 1, 1, BookStatus.YET,
          LocalDateTime.now(), LocalDateTime.now(), 1, new Member(), null);
      PageImpl<Book> bookPage = new PageImpl<>(List.of(book));
      given(bookService.findVerifiedBooksByMemberAndBookStatus(Mockito.anyInt(), Mockito.anyInt(),
          Mockito.any(Member.class), Mockito.any(BookStatus.class),
          Mockito.anyString())).willReturn(bookPage);

      List<BookDto.LibraryResponse> libraryResponses = new ArrayList<>();
      BookDto.LibraryResponse libraryResponse1 =
          new LibraryResponse(1, "미움받을용기", "https://imageurl.com", "아들러",
              LocalDateTime.now(), 3, 104, 507, BookStatus.YET, 3);
      BookDto.LibraryResponse libraryResponse2 =
          new LibraryResponse(12, "역사란 무엇인가", "https://imageurl.com", "김기명",
              LocalDateTime.now(), 0, 42, 114, BookStatus.YET, 0);
      libraryResponses.add(libraryResponse1);
      libraryResponses.add(libraryResponse2);
      given(bookMapper.BooksToLibraryResponse(Mockito.any())).willReturn(libraryResponses);

      //when
      ResultActions resultActions = mockMvc.perform(
          MockMvcRequestBuilders.get("/books/library").accept(MediaType.APPLICATION_JSON)
              .contentType(MediaType.APPLICATION_JSON).header("Authorization", Mockito.anyString())
              .queryParam("page", "1").queryParam("size", "10").queryParam("bookStatus", "YET"));

      //then
      resultActions.andExpect(status().isOk()).andDo(document("Library",
          preprocessRequest(modifyUris().scheme(SCHEMA).host(URI).removePort(), prettyPrint()),
          preprocessResponse(prettyPrint()),
          requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
          requestParameters(
              List.of(parameterWithName("page").description("원하는 page 수"),
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
                  .description("책 상태 : 읽기 전, 읽는 중, 읽기 완료"),
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
}
