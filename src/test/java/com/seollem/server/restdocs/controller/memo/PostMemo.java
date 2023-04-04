package com.seollem.server.restdocs.controller.memo;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.book.Book;
import com.seollem.server.book.BookService;
import com.seollem.server.file.FileUploadService;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.Memo.MemoAuthority;
import com.seollem.server.memo.Memo.MemoType;
import com.seollem.server.memo.MemoController;
import com.seollem.server.memo.MemoDto;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.restdocs.util.GsonCustomConfig;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(value = MemoController.class)
public class PostMemo extends WebMvcTestSetUpUtil {

  private final GsonCustomConfig gsonCustomConfig = new GsonCustomConfig();
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
  public void postMemoTest() throws Exception {

    Gson gson = gsonCustomConfig.gsonBuild();

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    doNothing().when(bookService).verifyMemberHasBook(Mockito.anyLong(), Mockito.anyLong());

    when(memoMapper.memoPostToMemo(Mockito.any())).thenReturn(new Memo());

    when(bookService.findVerifiedBookById(Mockito.anyLong())).thenReturn(new Book());

    when(memoMapper.memoToMemoResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockMemo.getMemoResponse());

    MemoDto.Post memoPost =
        new MemoDto.Post("메모 내용입니다.", 14, MemoType.BOOK_CONTENT, MemoAuthority.PUBLIC);
    String content = gson.toJson(memoPost);

    //when, then
    this.mockMvc.perform(
            post("/memos/{book-id}", 1).content(content).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .header("Authorization", "Bearer JWT Access Token")).andDo(print())
        .andExpect(status().isCreated()).andDo(document("PostMemo",
            requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
            pathParameters(parameterWithName("book-id").description("메모를 기록할 책의 id")),
            requestFields(fieldWithPath("memoContent").description("메모 내용"),
                fieldWithPath("memoBookPage").description("메모와 연관된 책 페이지"),
                fieldWithPath("memoType").description(
                    "메모 타입 : 전체(ALL), 책 속 문장(BOOK_CONTENT), 책 내용 요약(SUMMARY), 나만의 생각(THOUGHT), 나만의 질문(QUESTION)"),
                fieldWithPath("memoAuthority").description("메모 보기 권한 : PUBLIC, PRIVATE")),
            responseFields(fieldWithPath("memoId").description("Memo-id"),
                fieldWithPath("memoType").description(
                    "메모 타입 : 전체(ALL), 책 속 문장(BOOK_CONTENT), 책 내용 요약(SUMMARY), 나만의 생각(THOUGHT), 나만의 질문(QUESTION)"),
                fieldWithPath("memoContent").description("메모 내용"),
                fieldWithPath("memoBookPage").description("메모와 연관된 책 페이지"),
                fieldWithPath("memoAuthority").description("메모 보기 권한 : PUBLIC, PRIVATE"),
                fieldWithPath("memoLikesCount").description("메모 좋아요 개수"),
                fieldWithPath("createdAt").description("메모 생성 일자"),
                fieldWithPath("updatedAt").description("메모 수정 일자"))));
  }
}
