package tripagramex.domain.board.controller;

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
import tripagramex.domain.board.dto.CreateRequest;
import tripagramex.domain.board.enums.Category;
import tripagramex.global.security.authentication.UserAccount;
import tripagramex.global.security.jwt.JwtProcessor;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tripagramex.util.ApiDocumentUtils.getRequestPreProcessor;
import static tripagramex.util.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class BoardCRUDControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("게시물 생성_성공")
    public void createTest_Success() throws Exception {
        //given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        String title = "testTitle";
        String content = "testContent";
        String location = "testLocation";
        String thumbnail = "testThumbnail";
        Category category = Category.RESTAURANT;
        List<String> images = new ArrayList<>(List.of("testImage1", "testImage2"));

        CreateRequest createRequest = CreateRequest.builder()
                .title(title)
                .content(content)
                .location(location)
                .thumbnail(thumbnail)
                .category(category)
                .images(images)
                .build();

        String createRequestByJson = gson.toJson(createRequest);

        //when
        ResultActions create = mockMvc.perform(
                post("/boards")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestByJson)
        );

        //then
        create.andExpect(status().isCreated())
                .andDo(document(
                        "createBoard",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("location").description("위치"),
                                fieldWithPath("thumbnail").description("썸네일 URL"),
                                fieldWithPath("category").description("카테고리"),
                                fieldWithPath("images").description("이미지 리스트")
                                ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 Board 식별자")
                        )
                ));

    }

}