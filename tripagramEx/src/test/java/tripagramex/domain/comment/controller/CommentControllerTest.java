package tripagramex.domain.comment.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.comment.dto.CreateRequest;
import tripagramex.domain.comment.dto.CreateSubCommentRequest;
import tripagramex.domain.comment.dto.UpdateRequest;
import tripagramex.global.security.authentication.UserAccount;
import tripagramex.global.security.jwt.JwtProcessor;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tripagramex.util.ApiDocumentUtils.getRequestPreProcessor;
import static tripagramex.util.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("댓글 생성_성공")
    public void createComment_Success() throws Exception {
        //given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        Long boardId = 30001L;
        String content = "testComment";

        CreateRequest createRequest = CreateRequest.builder()
                .boardId(boardId)
                .content(content)
                .build();

        String createRequestByJson = gson.toJson(createRequest);

        //when
        ResultActions create = mockMvc.perform(
                post("/comments")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestByJson));

        //then
        create.andExpect(status().isCreated())
                .andDo(document(
                        "createComment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        requestFields(
                                fieldWithPath("boardId").description("게시물 식별자"),
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 Comment 식별자")
                        )
                ));
    }

    @Test
    @DisplayName("대댓글 생성_성공")
    public void createSubComment_Success() throws Exception {
        //given
        Long accountId = 10002L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        Long boardId = 30001L;
        Long commentId = 40001L;
        Long targetId = 10001L;
        String content = "testSubComments";

        CreateSubCommentRequest createSubCommentRequest = CreateSubCommentRequest.builder()
                .boardId(boardId)
                .commentId(commentId)
                .targetId(targetId)
                .content(content)
                .build();

        String createSubCommentRequestByJson = gson.toJson(createSubCommentRequest);

        //when
        ResultActions create = mockMvc.perform(
                post("/comments/subComment")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createSubCommentRequestByJson));

        //then
        create.andExpect(status().isCreated())
                .andDo(document(
                        "createSubComment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        requestFields(
                                fieldWithPath("boardId").description("게시물 식별자"),
                                fieldWithPath("commentId").description("댓글 식별자"),
                                fieldWithPath("targetId").description("대댓글 대상 식별자"),
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 SubComment 식별자")
                        )
                ));
    }

    @Test
    @DisplayName("댓글 수정_성공")
    public void updateComment_Success() throws Exception {
        //given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));
        Long commentId = 40001L;


        String content = "updateTestComment";

        UpdateRequest updateRequest = UpdateRequest.builder()
                .content(content)
                .build();

        String updateRequestByJson = gson.toJson(updateRequest);

        //when
        ResultActions update = mockMvc.perform(
                post("/comments/{commentId}", commentId)
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestByJson));

        update
                .andExpect(status().isOk())
                .andDo(document(
                        "updateComment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        requestFields(
                                fieldWithPath("content").description("수정할 내용").optional()
                        )
                ));

    }

}