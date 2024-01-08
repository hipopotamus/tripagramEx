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

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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

    @Test
    @DisplayName("댓글 삭제_성공")
    public void deleteComment_Success() throws Exception {
        //given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));
        Long commentId = 40001L;

        //when
        ResultActions delete = mockMvc.perform(
                delete("/comments/{commentId}", commentId)
                        .header("Authorization", jwt));

        //then
        delete
                .andExpect(status().isOk())
                .andDo(document(
                        "deleteComment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        )
                ));
    }

    @Test
    @DisplayName("게시물 댓글 조회_성공")
    public void readBoardComments() throws Exception {
        //given
        Long boardId = 30001L;

        //when
        ResultActions read = mockMvc.perform(
                get("/comments/board/{boardId}", boardId));

        //then
        read
                .andExpect(status().isOk())
                .andDo(document(
                        "readBoardComment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("boardId").description("Board 식별자")
                        ),
                        formParameters(
                                parameterWithName("page").description("페이지").optional(),
                                parameterWithName("size").description("페이지 크기").optional(),
                                parameterWithName("lastCommentId").description("마지막으로 조회된 댓글 식별자").optional()
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("댓글 목록"),
                                        fieldWithPath("content[].commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                                        fieldWithPath("content[].account").type(JsonFieldType.OBJECT).description("댓글 생성자"),
                                        fieldWithPath("content[].account.accountId").type(JsonFieldType.NUMBER).description("댓글 생성자 식별자"),
                                        fieldWithPath("content[].account.nickname").type(JsonFieldType.STRING).description("댓글 생성자 닉네임"),
                                        fieldWithPath("content[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                                        fieldWithPath("content[].modifiedAt").type(JsonFieldType.STRING).description("댓글 수정 일자"),
                                        fieldWithPath("content[].subCommentSize").type(JsonFieldType.NUMBER).description("대댓글 갯수"),
                                        fieldWithPath("sliceNumber").type(JsonFieldType.NUMBER).description("현재 슬라이스 번호"),
                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("슬라이스 사이즈"),
                                        fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 슬라이스 존재 여부"),
                                        fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("현재 슬라이스의 게시글 수")
                        )
                )));
    }

}