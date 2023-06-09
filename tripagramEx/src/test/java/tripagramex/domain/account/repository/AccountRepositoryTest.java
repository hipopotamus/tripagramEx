package tripagramex.domain.account.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import tripagramex.domain.account.entity.Account;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;
import tripagramex.util.TestConfig;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Email로 조회_성공")
    void findByEmail_Success() {

        //given
        String email = "test1@test.com";

        long expectedId = 10001L;
        String expectedNickname = "testNickname1";

        //when
        Account findAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        //then
        assertThat(findAccount.getEmail()).isEqualTo(email);
        assertThat(findAccount.getId()).isEqualTo(expectedId);
        assertThat(findAccount.getNickname()).isEqualTo(expectedNickname);
    }

    @Test
    @DisplayName("Email로 조회_DB에 맞는 계정이 없을 경우")
    void findByEmail_NotExistAccount() {

        //given
        String email = "notExist@test.com";

        //when
        boolean isAccountExist = accountRepository.findByEmail(email).isPresent();

        //then
        assertThat(isAccountExist).isFalse();
    }

    @Test
    @DisplayName("Email로 조회_대소문자가 다를 경우")
    void findByEmail_NotMatchUpperAndLower() {

        //given
        String email = "Test1@test.coom";

        //when
        boolean isAccountExist = accountRepository.findByEmail(email).isPresent();

        //then
        assertThat(isAccountExist).isFalse();
    }

    @Test
    @DisplayName("Email 존재 확인_존재 할 경우")
    void existsByEmail_Exist() {

        //given
        String email = "test1@test.com";

        //when
        boolean isEmailExist = accountRepository.existsByEmail(email);

        //then
        assertThat(isEmailExist).isTrue();
    }

    @Test
    @DisplayName("Email 존재 확인_존재하지 않을 경우")
    void existsByEmail_NotExist() {

        //given
        String email = "NotExist@test.com";

        //when
        boolean isEmailExist = accountRepository.existsByEmail(email);

        //then
        assertThat(isEmailExist).isFalse();
    }

    @Test
    @DisplayName("Nickname 존재 확인_존재 할 경우")
    void existsByNickname_Exist() {

        //given
        String nickname = "testNickname1";

        //when
        boolean isNicknameExist = accountRepository.existsByNickname(nickname);

        //then
        assertThat(isNicknameExist).isTrue();
    }

    @Test
    @DisplayName("Nickname 존재 확인_존재하지 않을 경우")
    void existsByNickname_NotExist() {

        //given
        String nickname = "notExistNickname";

        //when
        boolean isNicknameExist = accountRepository.existsByNickname(nickname);

        //then
        assertThat(isNicknameExist).isFalse();
    }
}