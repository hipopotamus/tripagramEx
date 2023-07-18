package tripagramex.domain.account.repository;

import org.springframework.stereotype.Repository;
import tripagramex.domain.account.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Account save(Account account);

    Optional<Account> findById(Long id);
}
