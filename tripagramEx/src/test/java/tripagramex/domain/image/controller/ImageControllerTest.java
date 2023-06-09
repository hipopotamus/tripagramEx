package tripagramex.domain.image.controller;

import org.junit.jupiter.api.DisplayName;
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
import tripagramex.global.security.authentication.UserAccount;
import tripagramex.global.security.jwt.JwtProcessor;
import tripagramex.util.Treatment;

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tripagramex.util.ApiDocumentUtils.getRequestPreProcessor;
import static tripagramex.util.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class ImageControllerTest extends Treatment {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JwtProcessor jwtProcessor;

    @Test
    @DisplayName("이미지 업로드_성공")
    void ImageUpload_Success() throws Exception {

        //given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        MockMultipartFile testImage1 = new MockMultipartFile("images", "jpgTestFile.jpg",
                "image/jpg", "(file data)".getBytes());
        MockMultipartFile testImage2 = new MockMultipartFile("images", "jpegTestFile.jpg",
                "image/jpeg", "(file data)".getBytes());
        MockMultipartFile testImage3 = new MockMultipartFile("images", "pngTestFile.jpg",
                "image/png", "(file data)".getBytes());

        //when
        ResultActions imageUploadResult = mockMvc.perform(
                multipart("/image-files")
                        .file(testImage1)
                        .file(testImage2)
                        .file(testImage3)
                        .header("Authorization", jwt)
        );

        //then
        imageUploadResult
                .andExpect(status().isOk())
                .andDo(document(
                        "imageUpload",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT")
                                )
                        ),
                        requestParts(
                                List.of(
                                        partWithName("images").description("이미지")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("imagePaths").type(JsonFieldType.ARRAY).description("이미지 주소 목록")
                                )
                        )

                ));
    }

}