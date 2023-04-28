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
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookController;
import com.seollem.server.book.BookDto;
import com.seollem.server.book.BookDto.Patch;
import com.seollem.server.restdocs.util.GsonCustomConfig;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForBookUtil;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(BookController.class)
public class PatchBook extends TestSetUpForBookUtil {

  private final GsonCustomConfig gsonCustomConfig = new GsonCustomConfig();

  @Test
  public void patchBookTest() throws Exception {
    Gson gson = gsonCustomConfig.gsonBuild();

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(bookService.verifyPatchBookStatus(Mockito.any())).thenReturn(
        StubDataUtil.MockBook.getBook());

    doNothing().when(bookService).verifyMemberHasBook(Mockito.anyLong(), Mockito.anyLong());

    when(bookService.updateBook(Mockito.any())).thenReturn(StubDataUtil.MockBook.getBook());

    when(bookMapper.BookToBookPatchResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockBook.getBookPatchResponse());

    BookDto.Patch patch =
        new Patch("알프레드 아들러", 511, 371, "독립출판", BookStatus.DONE,
            LocalDateTime.parse("2022-10-02T11:09:10"),
            LocalDateTime.parse("2022-10-13T21:04:32"), 5);
    String content = gson.toJson(patch);

    //when, then
    this.mockMvc.perform(
            patch("/books/{book-id}", 1).content(content).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .header("Authorization", "Bearer JWT Access Token")).andDo(print())
        .andExpect(status().isOk()).andDo(document("PatchBook",
            requestHeaders(headerWithName("Authorization").description("JWT Access Token")),
            pathParameters(parameterWithName("book-id").description("책 ID")),
            requestFields(fieldWithPath("author").description("저자"),
                fieldWithPath("itemPage").description("전체 페이지 수"),
                fieldWithPath("currentPage").description("현재 읽고 있는 페이지"),
                fieldWithPath("publisher").description("출판사"),
                fieldWithPath("bookStatus").description("책 읽기 상태 : YET(읽기 전), ING(읽는 중), DONE(읽기 완료)"),
                fieldWithPath("readStartDate").description("읽기 시작한 일자"),
                fieldWithPath("readEndDate").description("읽기 완료한 일자"),
                fieldWithPath("star").description("별점")),
            responseFields(fieldWithPath("author").description("저자"),
                fieldWithPath("publisher").description("출판사"),
                fieldWithPath("itemPage").description("전체 페이지 수"),
                fieldWithPath("readStartDate").description("읽기 시작한 일자"),
                fieldWithPath("readEndDate").description("읽기 완료한 일자"),
                fieldWithPath("bookStatus").description("책 읽기 상태 : YET(읽기 전), ING(읽는 중), DONE(읽기 완료)"),
                fieldWithPath("star").description("별점"),
                fieldWithPath("currentPage").description("현재 읽고 있는 페이지"))));

  }

}
