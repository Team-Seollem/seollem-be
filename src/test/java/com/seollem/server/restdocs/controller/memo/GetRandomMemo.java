package com.seollem.server.restdocs.controller.memo;


import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seollem.server.book.BookService;
import com.seollem.server.file.FileUploadService;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.MemoController;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(MemoController.class)
public class GetRandomMemo extends WebMvcTestSetUpUtil {

  @MockBean
  private MemoService memoService;
  @MockBean
  private MemoMapper memoMapper;
  @MockBean
  private MemberService memberService;
  @MockBean
  private GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  @MockBean
  private BookService bookService;
  @MockBean
  private FileUploadService fileUploadService;
  @MockBean
  private MemoLikesService memoLikesService;

  @Test
  public void getRandomMemoTest() throws Exception {
    //given
    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(memoService.randomMemo(Mockito.any())).thenReturn(new Memo());

    when(memoMapper.memoToRandomMemoResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockMemo.getRandomMemoResponse());

    //when
    ResultActions resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/memos/random").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer JWT Access Token"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("GetRandomMemo",
        requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
        responseFields(fieldWithPath("memoId").type(JsonFieldType.NUMBER).description("Memo-id"),
            fieldWithPath("memoContent").type(JsonFieldType.STRING).description("메모 내용"),
            fieldWithPath("memoType").type(JsonFieldType.STRING).description(
                "메모 타입 : 전체(ALL), 책 속 문장(BOOK_CONTENT), 책 내용 요약(SUMMARY), 나만의 생각(THOUGHT), 나만의 질문(QUESTION)"),
            fieldWithPath("memoBookPage").description("메모와 연관된 책 페이지"),
            fieldWithPath("createdAt").description("메모 생성 일자"),
            fieldWithPath("updatedAt").description("메모 수정 일자")

        )));
  }
}