package tripagramex.domain.account.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class AccountRepositoryImplTest {

    @Autowired
    private AccountRepository accountRepository;
}