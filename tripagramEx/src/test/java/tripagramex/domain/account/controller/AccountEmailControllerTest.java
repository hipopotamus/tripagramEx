package tripagramex.domain.account.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tripagramex.util.ApiDocumentUtils.getRequestPreProcessor;
import static tripagramex.util.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class AccountEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("임시 비밀번호 안내 메일 발송 성공")
    public void sendTempPasswordGuidMail_Success() throws Exception {
        //given
        String email = "test1@test.com";

        //when
        ResultActions sendTempPasswordGuidMail = mockMvc.perform(
                post("/accountEmail/tempPasswordGuid/{email}", email)
        );

        //then
        sendTempPasswordGuidMail
                .andExpect(status().isOk())
                .andDo(document(
                        "sendTempPasswordGuidMail",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("email").description("이메일")
                        )
                ));
    }

}