package tripagramex.domain.account.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;
import tripagramex.util.Treatment;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tripagramex.util.ApiDocumentUtils.getRequestPreProcessor;
import static tripagramex.util.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class AccountControllerTest extends Treatment {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void accountAddTest_Success() throws Exception {

        //given
        String email = "test@test.com";
        String password = "12345678";
        String nickname = "testNickname";
        MockMultipartFile profile = new MockMultipartFile("profile", "profile.jpg",
                "image/jpg", "(file data)".getBytes());

        //when
        ResultActions actions = mockMvc.perform(
                multipart("/accounts")
                        .file(profile)
                        .queryParam("email", email)
                        .queryParam("password", password)
                        .queryParam("nickname", nickname)
        );


        //then
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(account.getId()))
                .andDo(document(
                        "accountAdd",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParts(
                                partWithName("profile").description("프로필 이미지")
                        ),
                        queryParameters(
                                parameterWithName("email").description("이메일"),
                                parameterWithName("password").description("비밀번호"),
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 Account 식별자")
                        )
                ));
    }

}