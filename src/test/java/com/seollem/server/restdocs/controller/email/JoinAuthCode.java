package com.seollem.server.restdocs.controller.email;


import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.seollem.server.emailauth.EmailController;
import com.seollem.server.emailauth.EmailDto;
import com.seollem.server.emailauth.EmailService;
import com.seollem.server.member.MemberService;
import com.seollem.server.restdocs.util.GsonCustomConfig;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
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
@WebMvcTest(EmailController.class)
public class JoinAuthCode extends WebMvcTestSetUpUtil {

  private final GsonCustomConfig gsonCustomConfig = new GsonCustomConfig();
  @MockBean
  private EmailService emailService;
  @MockBean
  private MemberService memberService;


  @Test
  public void joinAuthCodeTest() throws Exception {
    Gson gson = gsonCustomConfig.gsonBuild();
    //given
    doNothing().when(memberService).verifyExistsEmail(Mockito.anyString());

    doNothing().when(emailService).send(Mockito.anyString());

    EmailDto.JoinAuthCodeReq emailRequestDto = new EmailDto.JoinAuthCodeReq("starrypro@gmail.com");
    String content = gson.toJson(emailRequestDto);

    //when, then
    this.mockMvc.perform(post("/email/join").accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON).characterEncoding(Charset.defaultCharset())
        .content(content)).andDo(print()).andExpect(status().isCreated()).andDo(
        document("JoinAuthCode", requestFields(
            fieldWithPath("joinAuthCodeEmail").type(JsonFieldType.STRING).description("이메일"))));
  }
}