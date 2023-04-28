package com.seollem.server.restdocs.controller.memolike;

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

import com.seollem.server.memolike.MemoLike;
import com.seollem.server.memolike.MemoLikeController;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForMemoLikeUtil;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(value = MemoLikeController.class)
public class DeleteMemoLike extends TestSetUpForMemoLikeUtil {

  @Test
  public void deleteMemoLikeTest() throws Exception {

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(memoLikeService.findVerifiedMemoLikeById(Mockito.anyLong())).thenReturn(new MemoLike());

    doNothing().when(memoLikeService).verifyMemberHasMemoLike(Mockito.any(), Mockito.any());
    doNothing().when(memoLikeService).deleteMemoLikeWithMemoLike(Mockito.any(MemoLike.class));

    //when, then
    this.mockMvc.perform(
            delete("/memo-like/{memo-like-id}", 1).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .header("Authorization", "Bearer JWT Access Token")).andDo(print())
        .andExpect(status().isNoContent()).andDo(document("DeleteMemoLike",
            requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
            pathParameters(parameterWithName("memo-like-id").description("메모좋아요 ID"))));

  }

}
