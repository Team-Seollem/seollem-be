package com.seollem.server.restdocs.controller.auth;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.seollem.server.restdocs.util.GsonCustomConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class Login {


  private final GsonCustomConfig gsonCustomConfig = new GsonCustomConfig();

  protected MockMvc mockMvc;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
            .withRequestDefaults(prettyPrint())
            .withResponseDefaults(prettyPrint())
            .and().uris().withScheme("https")
            .withHost("seollem.link").withPort(443))
        .apply(springSecurity()).build();
  }

  @WithMockUser
  @Test
  public void loginTest() throws Exception {
    Gson gson = gsonCustomConfig.gsonBuild();
    //given
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("email", "starrypro@gmail.com");
    jsonObject.addProperty("password", "Abcd123!");

    String content = gson.toJson(jsonObject);

    //when
    mockMvc.perform(
            post("/login")
                .content(content).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andDo(document("Login",
            requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
            ),
            responseHeaders(
                headerWithName("Authorization").description("Bearer \"ACCESS TOKEN...\""),
                headerWithName("Refresh").description("Bearer \"REFRESH TOKEN...\""))
        ));
  }
}
