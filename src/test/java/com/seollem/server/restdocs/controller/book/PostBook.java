package com.seollem.server.restdocs.controller.book;


import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookController;
import com.seollem.server.book.BookDto;
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
@WebMvcTest(value = BookController.class)
public class PostBook extends TestSetUpForBookUtil {

  private final GsonCustomConfig gsonCustomConfig = new GsonCustomConfig();


  @Test
  public void postBookTest() throws Exception {

    Gson gson = gsonCustomConfig.gsonBuild();

    //given
    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(bookService.verifyBookStatus(Mockito.any())).thenReturn(StubDataUtil.MockBook.getBook());

    when(bookService.createBook(Mockito.any())).thenReturn(StubDataUtil.MockBook.getBook());

    when(bookMapper.BookToBookPostResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockBook.getBookPostResponse());

    BookDto.Post post =
        new BookDto.Post("미움받을용기", "아들러", "https://imageurl1.com", 406, 214, "한빛출판사",
            BookStatus.DONE, LocalDateTime.parse("2022-10-02T11:09:10"),
            LocalDateTime.parse("2022-10-13T21:04:32"));
    String content = gson.toJson(post);

    //when, then
    this.mockMvc.perform(post("/books").content(content).contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(Charset.defaultCharset())
            .header("Authorization", "Bearer JWT Access Token")).andDo(print())
        .andExpect(status().isCreated()).andDo(document("PostBook",
            requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
            requestFields(fieldWithPath("title").description("제목"),
                fieldWithPath("author").description("저자"),
                fieldWithPath("cover").description("표지 이미지 URL"),
                fieldWithPath("itemPage").description("전체 페이지 수"),
                fieldWithPath("currentPage").description("현재 읽고 있는 페이지"),
                fieldWithPath("publisher").description("출판사"),
                fieldWithPath("bookStatus").description("책 읽기 상태 : YET(읽기 전), ING(읽는 중), DONE(읽기 완료)"),
                fieldWithPath("readStartDate").description("읽기 시작한 일자"),
                fieldWithPath("readEndDate").description("읽기 완료한 일자")),
            responseFields(fieldWithPath("bookId").description("책 ID"),
                fieldWithPath("title").description("제목"), fieldWithPath("author").description("저자"),
                fieldWithPath("cover").description("표지 이미지 URL"),
                fieldWithPath("bookStatus").description(
                    "책 읽기 상태 : YET(읽기 전), ING(읽는 중), DONE(읽기 완료)"))));
  }
}
