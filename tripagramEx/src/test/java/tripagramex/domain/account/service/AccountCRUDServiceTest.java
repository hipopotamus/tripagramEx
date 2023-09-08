package tripagramex.domain.account.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tripagramex.domain.account.dto.CreateRequest;
import tripagramex.domain.account.dto.ReadLoginAccountResponse;
import tripagramex.domain.account.dto.ReadResponse;
import tripagramex.domain.account.dto.UpdateRequest;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.mock.MockAccountRepository;
import tripagramex.domain.follow.repository.mock.MockFollowRepository;
import tripagramex.infrastructure.mock.MockPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AccountCRUDServiceTest {

    private final MockAccountRepository accountRepository = new MockAccountRepository();
    private final MockFollowRepository followRepository = new MockFollowRepository();
    private final MockPasswordEncoder passwordEncoder = new MockPasswordEncoder();
    private final AccountCRUDService accountCrudService = AccountCRUDService.builder()
            .accountRepository(accountRepository)
            .followRepository(followRepository)
            .passwordEncoder(passwordEncoder)
            .build();

    @AfterEach
    void after() {
        accountRepository.initiate();
    }

    @Test
    @DisplayName("계정 생성_성공")
    public void createTest_Success() {
        //given
        String email = "test@test.com";
        String password = "testPassword";
        String nickname = "testNickname";
        String profile = "testProfile";

        CreateRequest createRequest = CreateRequest.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .profile(profile)
                .build();

        //when
        accountCrudService.create(createRequest);

        //then
        Account account = accountRepository.findByEmail(email).get();
        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getPassword()).isEqualTo("[Encode]" + password);
        assertThat(account.getNickname()).isEqualTo(nickname);
        assertThat(account.getProfile()).isEqualTo(profile);


    }

    @Test
    @DisplayName("계정 단일 조회_성공")
    public void readTest_Success() {
        //given
        //when
        ReadResponse readResponse = accountCrudService.read(1L);

        //then
        assertThat(readResponse.getId()).isEqualTo(1L);
        assertThat(readResponse.getEmail()).isEqualTo("test1@test.com");
        assertThat(readResponse.getNickname()).isEqualTo("test1Nickname");
        assertThat(readResponse.getIntro()).isEqualTo("test1Intro");
        assertThat(readResponse.getProfile()).isEqualTo("test1Profile");
        assertThat(readResponse.getFollowing()).isEqualTo(0L);
        assertThat(readResponse.getFollower()).isEqualTo(0L);
    }

    @Test
    @DisplayName("로그인 계정 조회_성공")
    public void readLoginAccount_Success() {
        //given
        //when
        ReadLoginAccountResponse readLoginAccountResponse = accountCrudService.readLoginAccount(1L);

        //then
        assertThat(readLoginAccountResponse.getId()).isEqualTo(1L);
        assertThat(readLoginAccountResponse.getEmail()).isEqualTo("test1@test.com");
        assertThat(readLoginAccountResponse.getNickname()).isEqualTo("test1Nickname");
        assertThat(readLoginAccountResponse.getProfile()).isEqualTo("test1Profile");
    }

    @Test
    @DisplayName("계정 수정_성공")
    public void updateAccount_Success() {
        //given
        String updatePassword = "updatePassword";
        String updateNickname = "updateNickname";
        String updateProfile = "updateProfile";
        String updateIntro = "updateIntro";

        UpdateRequest updateRequest = UpdateRequest.builder()
                .password(updatePassword)
                .nickname(updateNickname)
                .profile(updateProfile)
                .intro(updateIntro)
                .build();
        //when
        accountCrudService.update(1L, updateRequest);

        //then
        Account account = accountRepository.findById(1L).get();
        assertThat(account.getPassword()).isEqualTo("[Encode]" + updatePassword);
        assertThat(account.getNickname()).isEqualTo(updateNickname);
        assertThat(account.getProfile()).isEqualTo(updateProfile);
        assertThat(account.getIntro()).isEqualTo(updateIntro);


    }

    @Test
    @DisplayName("계정 수정_Password, Nickname 없는 경우")
    public void updateAccount_NonExist_Password_Nickname() {
        //given
        String updateProfile = "updateProfile";
        String updateIntro = "updateIntro";

        UpdateRequest updateRequest = UpdateRequest.builder()
                .profile(updateProfile)
                .intro(updateIntro)
                .build();

        //when
        accountCrudService.update(1L, updateRequest);

        //then
        Account account = accountRepository.findById(1L).get();
        assertThat(account.getPassword()).isEqualTo("[Encode]test1Password");
        assertThat(account.getNickname()).isEqualTo("test1Nickname");
        assertThat(account.getProfile()).isEqualTo(updateProfile);
        assertThat(account.getIntro()).isEqualTo(updateIntro);

    }

    @Test
    @DisplayName("계정 삭제_성공")
    public void deleteAccount_Success() {
        //given
        //when
        accountCrudService.delete(1L);

        //then
        Optional<Account> optionalAccount = accountRepository.findById(1L);
        assertThat(optionalAccount.isPresent()).isFalse();
    }

}