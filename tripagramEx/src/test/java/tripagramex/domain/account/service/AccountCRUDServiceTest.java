package tripagramex.domain.account.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tripagramex.domain.account.dto.CreateRequest;
import tripagramex.domain.account.dto.IdDto;
import tripagramex.domain.account.dto.UpdateRequest;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.mock.MockAccountRepository;
import tripagramex.domain.follow.repository.mock.MockFollowRepository;
import tripagramex.infrastructure.mock.MockPasswordEncoder;

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
        accountRepository.clearAll();
    }

    @Test
    @DisplayName("Account 생성 Test_성공")
    public void createTest_Success() {
        //given
        CreateRequest createRequest = CreateRequest.builder()
                .email("test@test.com")
                .password("testPassword")
                .nickname("testNickname")
                .profile("testProfile")
                .build();

        //when
        IdDto idDto = accountCrudService.create(createRequest);

        //then
        assertThat(idDto.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Account 단일 조회 Test_성공")
    public void readTest_Success() {
        //given
        saveOneSample();

        //when
        //then
        accountCrudService.read(1L);
    }

    @Test
    @DisplayName("로그인 Account 조회 Test_성공")
    public void readLoginAccount_Success() {
        //given
        saveOneSample();

        //when
        //then
        accountCrudService.readLoginAccount(1L);
    }

    @Test
    @DisplayName("Account 수정 Test_성공")
    public void updateAccount_Success() {
        //given
        saveOneSample();

        //when
        UpdateRequest updateRequest = UpdateRequest.builder()
                .password("testPassword")
                .nickname("testNickname")
                .profile("testProfile")
                .intro("testIntro")
                .build();

        //then
        accountCrudService.update(1L, updateRequest);
    }

    @Test
    @DisplayName("Account 수정 Test_Password, Nickname 없는 경우")
    public void updateAccount_NonExist_Password_Nickname() {
        //given
        saveOneSample();

        //when
        UpdateRequest updateRequest = UpdateRequest.builder()
                .profile("testProfile")
                .intro("testIntro")
                .build();

        //then
        accountCrudService.update(1L, updateRequest);
    }

    private void saveOneSample() {
        Account account = Account.builder()
                .id(1L)
                .email("test1@test.com")
                .password("[Encode]testPassword")
                .nickname("testNickname")
                .profile("testProfile")
                .build();
        accountRepository.save(account);
    }

}