package tripagramex.domain.account.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import tripagramex.util.TestConfig;

@DataJpaTest
@Import(TestConfig.class)
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;
}