package tripagramex.domain.account.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tripagramex.domain.account.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository {
    @Query("select account from Account account " +
            "where account.email = :email and account.deleted = false")
    Optional<Account> findByEmail(@Param("email") String email);

    @Query("select count(account) > 0 from Account account " +
            "where account.email = :email and account.deleted = false")
    boolean existsByEmail(@Param("email") String email);

    @Query("select count(account) > 0 from Account account " +
            "where account.nickname = :nickname and account.deleted = false")
    boolean existsByNickname(@Param("nickname") String nickname);

    Account save(Account account);

    @Query("select account from Account account " +
            "where account.id = :id and account.deleted = false")
    Optional<Account> findById(@Param("id") Long id);

    @Query("select count(account) > 0 from Account account " +
            "where account.id = :id and account.deleted = false")
    boolean existsById(@Param("id") Long id);
}
