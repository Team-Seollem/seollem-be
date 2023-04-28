package com.seollem.server.restdocs.controller.memo;

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

import com.seollem.server.book.BookService;
import com.seollem.server.file.FileUploadService;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.MemoController;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolike.MemoLikeService;
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
public class DeleteMemo extends WebMvcTestSetUpUtil {

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
  private MemoLikeService memoLikeService;

  @Test
  public void deleteMemoTest() throws Exception {

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    doNothing().when(memoService).verifyMemberHasMemo(Mockito.anyLong(), Mockito.anyLong());

    doNothing().when(memoService).deleteMemo(Mockito.anyLong());

    //when, then
    this.mockMvc.perform(
            delete("/memos/{memo-id}", 1).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .header("Authorization", "Bearer JWT Access Token"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andDo(document("DeleteMemo",
            requestHeaders(
                headerWithName("Authorization").description("Bearer JWT Access Token")
            ),
            pathParameters(
                parameterWithName("memo-id").description("삭제할 메모 ID")
            )
        ));

  }

}
