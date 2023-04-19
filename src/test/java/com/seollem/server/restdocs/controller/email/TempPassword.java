package com.seollem.server.restdocs.controller.email;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.member.MemberService;
import com.seollem.server.restdocs.util.GsonCustomConfig;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import com.seollem.server.temppassword.TempPasswordController;
import com.seollem.server.temppassword.TempPasswordDto;
import com.seollem.server.temppassword.TempPasswordService;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(TempPasswordController.class)
public class TempPassword extends WebMvcTestSetUpUtil {

  private final GsonCustomConfig gsonCustomConfig = new GsonCustomConfig();
  @MockBean
  private TempPasswordService tempPasswordService;
  @MockBean
  private MemberService memberService;


  @Test
  public void tempPasswordTest() throws Exception {
    Gson gson = gsonCustomConfig.gsonBuild();
    //given
    when(tempPasswordService.createTempPassword()).thenReturn("password");

    when(memberService.findVerifiedMemberByEmail(Mockito.anyString())).thenReturn(
        StubDataUtil.MockMember.getMember());

    when(memberService.updateMember(Mockito.any())).thenReturn(StubDataUtil.MockMember.getMember());

    doNothing().when(tempPasswordService).send(Mockito.anyString(), Mockito.anyString());

    TempPasswordDto tempPasswordDto = new TempPasswordDto("starrypro@gmail.com");
    String content = gson.toJson(tempPasswordDto);

    //when, then
    this.mockMvc.perform(post("/email/password-change").accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON).characterEncoding(Charset.defaultCharset())
        .content(content)).andDo(print()).andExpect(status().isCreated()).andDo(
        document("TempPassword", requestFields(
            fieldWithPath("tempPasswordEmail").type(JsonFieldType.STRING).description("이메일"))));
  }
}