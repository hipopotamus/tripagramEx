package tripagramex.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.account.entity.Account;

import java.util.Optional;

public interface accountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);
}
