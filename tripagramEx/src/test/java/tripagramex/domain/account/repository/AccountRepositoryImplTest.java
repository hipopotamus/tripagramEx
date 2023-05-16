package tripagramex.domain.account.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;

import java.util.List;

@Transactional
@SpringBootTest
class AccountRepositoryImplTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("QueryDsl_테스트")
    void queryDslTest() {
        List<Account> testAccountList = accountRepository.findTestAccountList();
        Assertions.assertThat(testAccountList).isEmpty();
    }
}