package com.seollem.server.restdocs.controller.externallibrary;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seollem.server.externallibrary.controller.AladdinController;
import com.seollem.server.externallibrary.service.AladdinService;
import com.seollem.server.externallibrary.util.BuildAladdinUriUtil;
import com.seollem.server.restdocs.util.StubDataUtil;
import com.seollem.server.restdocs.util.WebMvcTestSetUpUtil;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(AladdinController.class)
public class GetItemNewSpecial extends WebMvcTestSetUpUtil {

  @MockBean
  private BuildAladdinUriUtil buildAladdinUriUtil;
  @MockBean
  private AladdinService aladdinService;

  @Test
  public void getBestSellerTest() throws Exception {
    //given
    when(buildAladdinUriUtil.buildSearchUri(Mockito.anyString())).thenReturn(URI.create(""));

    when(aladdinService.getResponse(Mockito.any())).thenReturn(
        StubDataUtil.MockAladdin.getItemNewSpecial());

    //when
    ResultActions resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/ext-lib/item-new-special"));

    //then
    resultActions.andExpect(status().isOk()).andDo(document("GetAladdinItemNewSpecial",
        responseFields(
            fieldWithPath("response").type(JsonFieldType.STRING).description("신간리스트 조회 결과 데이터")
        )));
  }
}