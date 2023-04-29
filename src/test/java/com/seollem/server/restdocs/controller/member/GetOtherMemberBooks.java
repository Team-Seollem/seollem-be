package com.seollem.server.restdocs.controller.member;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seollem.server.member.MemberController;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForMemberUtil;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(MemberController.class)
public class GetOtherMemberBooks extends TestSetUpForMemberUtil {


  @Test
  public void getOtherMemberBooksTest() throws Exception {
    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(bookService.findVerifiedBookById(Mockito.anyLong())).thenReturn(
        StubDataUtil.MockBook.getBook());

    when(bookService.getOtherMemberBook(Mockito.anyLong())).thenReturn(
        StubDataUtil.MockBook.getOtherMemberBookDto());

    when(memoService.getOtherMemberBookMemosWithBook(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.any())).thenReturn(StubDataUtil.MockBook.getPageOtherMemberBookMemoDto());

    when(memoService.getPageMemosWithBookAndMemoAuthority(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.any(),
        Mockito.any())).thenReturn(StubDataUtil.MockMemo.getMemoPage());

    when(memoLikeService.findMemoLikesDone(Mockito.any(), Mockito.any())).thenReturn(
        new ArrayList<>());

    when(memoLikeService.getMemoLikesCountWithMemos(Mockito.any())).thenReturn(new ArrayList<>());

    when(memberMapper.toOtherMemberBookResponseDto(Mockito.any(), Mockito.any(), Mockito.any(),
        Mockito.any(), Mockito.any())).thenReturn(
        StubDataUtil.MockMember.getOtherMemberBookResponse());

    //when
    ResultActions resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/members/other/books/{book-id}", 1)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer JWT Access Token").queryParam("page", "1")
            .queryParam("size", "2"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("GetOtherMemberBooks",
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        pathParameters(parameterWithName("book-id").description("조회할 책 ID")),
        requestParameters(parameterWithName("page").description("원하는 memoList 의 page"),
            parameterWithName("size").description("페이지 별 메모 개수")),
        responseFields(fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("author").type(JsonFieldType.STRING).description("저자"),
            fieldWithPath("cover").type(JsonFieldType.STRING).description("표지 이미지 URL"),
            fieldWithPath("publisher").type(JsonFieldType.STRING).description("출판사"),
            fieldWithPath("itemPage").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
            fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 읽고 있는 페이지"),
            fieldWithPath("star").type(JsonFieldType.NUMBER).description("별점"),
            fieldWithPath("bookStatus").type(JsonFieldType.STRING)
                .description("책 읽기 상태 : YET(읽기 전), ING(읽는 중), DONE(읽기 완료)"),
            fieldWithPath("memoList[].memoId").description("메모 ID"),
            fieldWithPath("memoList[].memoType").description(
                "메모 타입 : 책 속 문장(BOOK_CONTENT), 책 내용 요약(SUMMARY), 나만의 생각(THOUGHT), 나만의 질문(QUESTION), 전체(ALL)"),
            fieldWithPath("memoList[].memoContent").description("메모 내용"),
            fieldWithPath("memoList[].memoBookPage").description("메모와 연관된 책 페이지"),
            fieldWithPath("memoList[].memoLikesCount").description("메모에 달린 좋아요 개수"),
            fieldWithPath("memoList[].memoLikeDone").description(
                "해당 회원의 메모 좋아요 여부 : True(O), False(X)"),
            fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER)
                .description("조회한 memoList 페이지"),
            fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지별 메모 개수"),
            fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER)
                .description("조회된 메모의 총 개수"),
            fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER)
                .description("조회된 총 페이지 수")

        )));
  }
}