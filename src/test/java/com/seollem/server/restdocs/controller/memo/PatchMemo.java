package com.seollem.server.restdocs.controller.memo;

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
import com.seollem.server.book.BookService;
import com.seollem.server.file.FileUploadService;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.Memo.MemoAuthority;
import com.seollem.server.memo.Memo.MemoType;
import com.seollem.server.memo.MemoController;
import com.seollem.server.memo.MemoDto;
import com.seollem.server.memo.MemoDto.Patch;
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
@WebMvcTest(MemoController.class)
public class PatchMemo extends WebMvcTestSetUpUtil {

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
  public void patchMemoTest() throws Exception {
    Gson gson = gsonCustomConfig.gsonBuild();

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(memoService.updateMemo(Mockito.any())).thenReturn(new Memo());

    when(memoMapper.memoToMemoResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockMemo.getMemoResponse());

    MemoDto.Patch patch =
        new Patch(1, "1번 메모의 내용입니다.", MemoType.BOOK_CONTENT, 14, MemoAuthority.PUBLIC);
    String content = gson.toJson(patch);

    //when, then
    this.mockMvc.perform(
            patch("/memos/{memo-id}", 1).content(content).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .header("Authorization", "Bearer JWT Access Token"))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("PatchMemo",
            requestHeaders(
                headerWithName("Authorization").description("Bearer JWT Access Token")
            ),
            pathParameters(
                parameterWithName("memo-id").description("수정할 메모 id")
            ),
            requestFields(
                fieldWithPath("memoId").description("메모 id"),
                fieldWithPath("memoContent").description("변경할 메모 내용"),
                fieldWithPath("memoType").description(
                    "변경할 메모 타입 : 전체(ALL), 책 속 문장(BOOK_CONTENT), 책 내용 요약(SUMMARY), 나만의 생각(THOUGHT), 나만의 질문(QUESTION)"),
                fieldWithPath("memoBookPage").description("변경할 메모와 연관된 책 페이지"),
                fieldWithPath("memoAuthority").description("변경할 메모 보기 권한 : PUBLIC, PRIVATE")
            ),
            responseFields(
                fieldWithPath("memoId").description("Memo-id"),
                fieldWithPath("memoType").description(
                    "메모 타입 : 전체(ALL), 책 속 문장(BOOK_CONTENT), 책 내용 요약(SUMMARY), 나만의 생각(THOUGHT), 나만의 질문(QUESTION)"),
                fieldWithPath("memoContent").description("메모 내용"),
                fieldWithPath("memoBookPage").description("메모와 연관된 책 페이지"),
                fieldWithPath("memoAuthority").description("메모 보기 권한 : PUBLIC, PRIVATE"),
                fieldWithPath("memoLikesCount").description("메모 좋아요 개수"),
                fieldWithPath("createdAt").description("메모 생성 일자"),
                fieldWithPath("updatedAt").description("메모 수정 일자"))
        ));

  }

}
