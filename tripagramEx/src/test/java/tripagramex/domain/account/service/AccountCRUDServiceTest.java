package tripagramex.domain.account.service;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import tripagramex.domain.account.dto.CreateRequest;
import tripagramex.domain.account.dto.IdDto;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.account.repository.mock.MockAccountRepository;
import tripagramex.domain.follow.repository.FollowRepository;
import tripagramex.domain.follow.repository.mock.MockFollowRepository;
import tripagramex.domain.image.service.ImageService;
import tripagramex.domain.image.service.mock.MockImageService;
import tripagramex.global.intrastructure.PasswordEncoder;
import tripagramex.infrastructure.mock.MockPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class AccountCRUDServiceTest {

    private final AccountRepository accountRepository = new MockAccountRepository();
    private final FollowRepository followRepository = new MockFollowRepository();
    private final ImageService imageService = new MockImageService();
    private final PasswordEncoder passwordEncoder = new MockPasswordEncoder();
    private final AccountCRUDService accountCrudService = new AccountCRUDService(accountRepository, followRepository,
            passwordEncoder, imageService);

    @Test
    public void createTest_Success() {
        //given
        MockMultipartFile profile = new MockMultipartFile("profile", "profile.jpeg",
                "image/jpeg", "(file data)".getBytes());
        CreateRequest createRequest = CreateRequest.builder()
                .email("test@test.com")
                .password("testPassword")
                .nickname("testNickname")
                .profile(profile)
                .build();

        //when
        IdDto idDto = accountCrudService.create(createRequest);

        //then
        assertThat(idDto.getId()).isEqualTo(1L);
    }

}