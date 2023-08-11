package tripagramex.domain.account.controller;

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
import tripagramex.domain.account.dto.CreateRequest;
import tripagramex.domain.account.dto.UpdateRequest;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.global.security.authentication.UserAccount;
import tripagramex.global.security.dto.LoginDto;
import tripagramex.global.security.jwt.JwtProcessor;
import tripagramex.util.Treatment;

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
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
class AccountCRUDControllerTest extends Treatment {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("로그인 성공")
    void loginTest_Success() throws Exception {

        //given
        String email = "test1@test.com";
        String password = "12345678";

        LoginDto loginDto = new LoginDto(email, password);
        String content = gson.toJson(loginDto);

        //when
        ResultActions loginResult = mockMvc.perform(
                post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        loginResult
                .andExpect(status().isOk())
                .andDo(document(
                        "login",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseHeaders(
                                headerWithName("Authorization").description("JWT")
                        )
                ));
    }

    @Test
    @DisplayName("계정 추가_성공")
    void createTest_Success() throws Exception {

        //given
        String email = "test@test.com";
        String password = "12345678";
        String nickname = "testNickname";
        String profile = "testProfile";

        CreateRequest createRequest = CreateRequest.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .profile(profile)
                .build();

        String content = gson.toJson(createRequest);

        //when
        ResultActions createResult = mockMvc.perform(
                post("/accounts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)

        );


        //then
        createResult
                .andExpect(status().isCreated())
                .andDo(document(
                        "create",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("profile").description("프로필 주소")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 Account 식별자")
                        )
                ));
    }

    @Test
    @DisplayName("계정 단일 조회_성공")
    void readTest_Success() throws Exception {
        //given
        Long accountId = 10001L;

        //when
        ResultActions actions = mockMvc.perform(
                get("/accounts/{accountId}", accountId)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "read",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("accountId").description("Account 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Account 식별자"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("intro").type(JsonFieldType.STRING).description("소개글"),
                                        fieldWithPath("profile").type(JsonFieldType.STRING).description("프로필 이미지"),
                                        fieldWithPath("following").type(JsonFieldType.NUMBER).description("팔로잉 수"),
                                        fieldWithPath("follower").type(JsonFieldType.NUMBER).description("팔로워 수")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("로그인 계정 조회_성공")
    void readLoginAccountTest_Success() throws Exception {
        //given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        //when
        ResultActions readLoginAccount = mockMvc.perform(
                get("/accounts/login")
                        .header("Authorization", jwt)
        );

        //then
        readLoginAccount
                .andExpect(status().isOk())
                .andDo(document(
                        "readLoginAccount",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Account 식별자"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("profile").type(JsonFieldType.STRING).description("프로필 이미지")
                        )
                ));
    }

    @Test
    @DisplayName("계정 수정 성공")
    void updateTest_Success() throws Exception {
        //given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .password("updatePassword")
                .nickname("updateNickname")
                .profile("updateProfile")
                .intro("updateIntro")
                .build();

        String content = gson.toJson(updateRequest);

        //when
        ResultActions update = mockMvc.perform(
                post("/accounts/update")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        update.andExpect(status().isOk())
                .andDo(document(
                        "update",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        requestFields(
                                fieldWithPath("password").description("비밀 번호").optional(),
                                fieldWithPath("nickname").description("닉네임").optional(),
                                fieldWithPath("intro").description("소개글").optional(),
                                fieldWithPath("profile").description("프로필 이미지 경로").optional()
                        ),
                        responseFields(
                                fieldWithPath("id").description("수정된 계정의 id")
                        )
                ));
    }

    @Test
    @DisplayName("계정 삭제 성공")
    void deleteTest_Success() throws Exception {
        //given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        //when
        ResultActions delete = mockMvc.perform(
                delete("/accounts")
                        .header("Authorization", jwt)
        );

        //then
        delete.andExpect(status().isOk())
                .andDo(document(
                        "delete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        )
                ));
    }

}