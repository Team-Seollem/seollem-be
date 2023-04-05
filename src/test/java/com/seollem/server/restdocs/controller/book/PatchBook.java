package com.seollem.server.restdocs.controller.book;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.book.Book;
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookController;
import com.seollem.server.book.BookDto;
import com.seollem.server.book.BookDto.Patch;
import com.seollem.server.book.BookMapper;
import com.seollem.server.book.BookService;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.restdocs.util.GsonCustomConfig;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(BookController.class)
public class PatchBook extends WebMvcTestSetUpUtil {

  private final GsonCustomConfig gsonCustomConfig = new GsonCustomConfig();
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

  @Test
  public void patchBookTest() throws Exception {
    Gson gson = gsonCustomConfig.gsonBuild();

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(bookService.verifyBookStatus(Mockito.any())).thenReturn(new Book());

    doNothing().when(bookService).verifyMemberHasBook(Mockito.anyLong(), Mockito.anyLong());

    when(bookService.updateBook(Mockito.any())).thenReturn(new Book());

    when(bookMapper.BookToBookPatchResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockBook.getBookPatchResponse());

    BookDto.Patch patch = new Patch("김지준", 221, 220, "한빛출판사", BookStatus.YET, LocalDateTime.now(),
        LocalDateTime.now(), 5);
    String content = gson.toJson(patch);

    //when, then
    this.mockMvc.perform(
            patch("/books/{book-id}", 1).content(content).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .header("Authorization", "Bearer JWT Access Token"))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("PatchBook",
            requestHeaders(
                headerWithName("Authorization").description("JWT Access Token")
            ),
            pathParameters(
                parameterWithName("book-id").description("수정할 책 id")
            ),
            requestFields(
                fieldWithPath("author").description("저자"),
                fieldWithPath("itemPage").description("책 전체 페이지 수"),
                fieldWithPath("currentPage").description("현재 읽고 있는 페이지"),
                fieldWithPath("publisher").description("출판사"),
                fieldWithPath("bookStatus").description("읽기 상태 : 읽기 전, 읽는 중, 읽기 완료"),
                fieldWithPath("readStartDate").description("읽기 시작 일자"),
                fieldWithPath("readEndDate").description("읽기 완료 일자"),
                fieldWithPath("star").description("별점")
            ),
            responseFields(
                fieldWithPath("author").description("저자"),
                fieldWithPath("publisher").description("출판사"),
                fieldWithPath("itemPage").description("책 전체 페이지 수"),
                fieldWithPath("readStartDate").description("읽기 시작 일자"),
                fieldWithPath("readEndDate").description("읽기 완료 일자"),
                fieldWithPath("bookStatus").description("읽기 상태 : 읽기 전, 읽는 중, 읽기 완료"),
                fieldWithPath("star").description("별점"),
                fieldWithPath("currentPage").description("현재 읽고 있는 페이지")
            )
        ));

  }

}
