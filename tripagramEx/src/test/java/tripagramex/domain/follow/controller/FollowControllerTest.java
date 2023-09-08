package tripagramex.domain.follow.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.global.security.authentication.UserAccount;
import tripagramex.global.security.jwt.JwtProcessor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tripagramex.util.ApiDocumentUtils.getRequestPreProcessor;
import static tripagramex.util.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Test
    @DisplayName("팔로우 시행_성공")
    void postFollow_Success() throws Exception {
        //given
        Long followerId = 10001L;
        Long followingId = 10002L;
        Account loginAccount = accountRepository.findById(followerId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(loginAccount));

        //when
        ResultActions postFollow = mockMvc.perform(
                post("/follow/{followingId}", followingId)
                        .header("Authorization", jwt)
        );

        //then
        postFollow
                .andExpect(status().isOk())
                .andDo(document(
                        "postFollow",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        pathParameters(
                                parameterWithName("followingId").description("Following 대상 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("Follow 시행 결과")
                                        )
                        )
                ));
    }

    @Test
    @DisplayName("팔로우 여부 확인_성공")
    void checkFollowTest_Success() throws Exception {
        //given
        Long followerId = 10001L;
        Long followingId = 10002L;
        Account loginAccount = accountRepository.findById(followerId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(loginAccount));

        //when
        ResultActions checkFollow = mockMvc.perform(
                get("/follow/{followingId}", followingId)
                        .header("Authorization", jwt)
        );

        //then
        checkFollow
                .andExpect(status().isOk())
                .andDo(document(
                        "checkFollow",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        pathParameters(
                                parameterWithName("followingId").description("Following 대상 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("follow").type(JsonFieldType.BOOLEAN).description("Follow 여부")
                                )
                        )
                ));

    }

}