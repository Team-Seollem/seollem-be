package com.seollem.server.restdocs.controller.book;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seollem.server.book.BookController;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForBookUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(value = BookController.class)
public class DeleteBook extends TestSetUpForBookUtil {

  @Test
  public void deleteBookTest() throws Exception {
    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(bookService.verifyBookStatus(Mockito.any())).thenReturn(StubDataUtil.MockBook.getBook());

    doNothing().when(bookService).deleteBookWithId(Mockito.anyLong());

    //when, then
    this.mockMvc.perform(delete("/books/{book-id}", 1)
            .header("Authorization", "Bearer JWT Access Token"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andDo(document("DeleteBook",
            requestHeaders(
                headerWithName("Authorization").description("JWT Access Token")
            ),
            pathParameters(
                parameterWithName("book-id").description("책 ID")
            )
        ));
  }
}
