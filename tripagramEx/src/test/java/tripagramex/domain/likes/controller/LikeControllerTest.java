package tripagramex.domain.likes.controller;

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
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Test
    @DisplayName("좋아요 시행_성공")
    void postLikeTest_Success() throws Exception {
        //given
        Long loginAccountId = 10001L;
        Long boardId = 30002L;
        Account loginAccount = accountRepository.findById(loginAccountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(loginAccount));

        //when
        ResultActions postLike = mockMvc.perform(
                post("/likes/{boardId}", boardId)
                        .header("Authorization", jwt)

        );

        //then
        postLike
                .andExpect(status().isOk())
                .andDo(document(
                        "postLike",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("좋아요 대상 게시물 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("좋아요 시행 결과")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("좋아요 여부 확인 조회_성공")
    void checkLikeTest_Success() throws Exception {
        //given
        Long loginAccountId = 10001L;
        Long boardId = 30002L;
        Account loginAccount = accountRepository.findById(loginAccountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(loginAccount));

        //when
        ResultActions checkLike = mockMvc.perform(
                get("/likes/check/{boardId}", boardId)
                        .header("Authorization", jwt));

        //then
        checkLike
                .andExpect(status().isOk())
                .andDo(document(
                        "checkLike",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("좋아요 대상 게시물 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("likes").type(JsonFieldType.BOOLEAN).description("좋아요 여부")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("게시물의 좋아요 개수 조회_성공")
    void countBoardLikeTest_Success() throws Exception {
        //given
        Long boardId = 30002L;

        //when
        ResultActions countBoardLike = mockMvc.perform(
                get("/likes/{boardId}", boardId)
        );

        //then
        countBoardLike
                .andExpect(status().isOk())
                .andDo(document(
                        "countBoardLike",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("boardId").description("게시물 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("게시물의 좋아요 개수")
                                )
                        )
                ));
    }
}