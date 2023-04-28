package com.seollem.server.restdocs.controller.member;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seollem.server.member.MemberController;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.TestSetUpForMemberUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(value = MemberController.class)
public class PostMemberImage extends TestSetUpForMemberUtil {


  @Test
  public void postMemberImageTest() throws Exception {

    //given
    when(getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(Mockito.anyMap())).thenReturn(
        "starrypro@gmail.com");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(fileUploadService.createImage(Mockito.any())).thenReturn("https://imageurl.com");

    when(memberService.updateMemberImage(Mockito.any(), Mockito.any())).thenReturn(
        StubDataUtil.MockMember.getMember());

    //when, then
    this.mockMvc.perform(
            multipart("/members/member-image").file("file", "file".getBytes())
                .header("Authorization", "Bearer JWT Access Token")).andDo(print())
        .andExpect(status().isCreated()).andDo(document("PostMemberImage",
            requestHeaders(headerWithName("Authorization").description("Bearer JWT Access Token")),
            requestParts(partWithName("file").description("업로드할 이미지 파일 : form-data")),
            responseFields(fieldWithPath("url").description("업로드된 이미지의 URL"))
        ));
  }
}