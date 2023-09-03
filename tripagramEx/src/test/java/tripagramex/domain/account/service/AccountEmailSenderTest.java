package tripagramex.domain.account.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.mock.MockAccountRepository;
import tripagramex.domain.email.service.mock.MockEmailSender;
import tripagramex.infrastructure.mock.MockPasswordEncoder;
import tripagramex.infrastructure.mock.MockTemplateManager;

import static org.assertj.core.api.Assertions.assertThat;

class AccountEmailSenderTest {

    private final MockAccountRepository accountRepository = new MockAccountRepository();
    private final MockEmailSender emailSender = new MockEmailSender();
    private final MockTemplateManager templateManager = new MockTemplateManager();
    private final MockPasswordEncoder passwordEncoder = new MockPasswordEncoder();
    private final AccountEmailService accountEmailService = AccountEmailService.builder()
            .accountRepository(accountRepository)
            .emailSender(emailSender)
            .templateManager(templateManager)
            .passwordEncoder(passwordEncoder)
            .build();

    @AfterEach
    void after() {
        accountRepository.clearAll();
    }

    @Test
    @DisplayName("임시 비밀번호 안내문 발송_성공")
    public void sendTempPasswordGuid_Success() {
        //given
        String backDomain = "localhost:8080/";

        //when then
        accountEmailService.sendTempPasswordGuid("test1@test.com", backDomain);
    }

    @Test
    @DisplayName("임시 비밀번호 적용_성공")
    public void applyTempPassword_Success() {
        //given
        String tempPassword = "TempPassword";

        //when
        accountEmailService.applyTempPassword(1L, tempPassword);

        //then
        Account account = accountRepository.findById(1L).get();
        assertThat(account.getPassword()).isEqualTo("[Encode]" + tempPassword);
    }

}