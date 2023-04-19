package com.seollem.server.restdocs.controller.book;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.book.BookController;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForBookUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class GetCalender extends TestSetUpForBookUtil {

  @Autowired
  private Gson gson;

  @Test
  public void calenderTest() throws Exception {
    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    ArrayList<LocalDateTime> tempList = new ArrayList<>();
    tempList.add(LocalDateTime.now());
    tempList.add(LocalDateTime.now());
    when(getAbandonPeriodUtil.getCalenderBookPeriod(Mockito.anyInt(), Mockito.anyInt())).thenReturn(
        tempList);

    when(bookService.findCalenderBooks(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(),
        Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyString())).thenReturn(
        StubDataUtil.MockBook.getBookPage());

    given(bookMapper.BooksToCalenderResponse(Mockito.any())).willReturn(
        StubDataUtil.MockBook.getCalenderResponse());

    //when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/books/calender").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer JWT Access Token").queryParam("page", "1")
            .queryParam("size", "2").queryParam("year", "2022").queryParam("month", "10"));

    //then
    resultActions.andExpect(status().isOk()).andDo(print()).andDo(document("Calender",
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        requestParameters(parameterWithName("page").description("원하는 page"),
            parameterWithName("size").description("페이지 별 책 개수"),
            parameterWithName("year").description("조회할 읽기 완료 년도"),
            parameterWithName("month").description("조회할 읽기 완료 월")), responseFields(
            fieldWithPath("item[].bookId").type(JsonFieldType.NUMBER).description("책 ID"),
            fieldWithPath("item[].readEndDate").type(JsonFieldType.STRING).description("읽기 완료한 일자"),
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