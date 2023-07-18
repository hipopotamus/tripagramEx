package tripagramex.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.account.entity.Account;

public interface AccountRepositoryByJPA extends AccountRepository, AccountRepositoryByQueryDsl, JpaRepository<Account, Long> {
}
