package com.seollem.server.restdocs.controller.memolike;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seollem.server.memo.Memo;
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
public class PostMemoLike extends TestSetUpForMemoLikeUtil {

  @Test
  public void postMemoLikeTest() throws Exception {

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(memoService.findVerifiedMemo(Mockito.anyLong())).thenReturn(new Memo());

    when(memoLikeService.createMemoLike(Mockito.any(), Mockito.any())).thenReturn(
        StubDataUtil.MockMemoLike.getMemoLike());

    //when, then
    this.mockMvc.perform(
            post("/memo-like/{memo-id}", 1).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .header("Authorization", "Bearer JWT Access Token")).andDo(print())
        .andExpect(status().isCreated()).andDo(document("PostMemoLike",
            requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
            pathParameters(parameterWithName("memo-id").description("메모 좋아요를 등록할 메모 ID")),
            responseFields(fieldWithPath("memoLikeId").description("등록된 메모 좋아요 ID"))));

  }

}
