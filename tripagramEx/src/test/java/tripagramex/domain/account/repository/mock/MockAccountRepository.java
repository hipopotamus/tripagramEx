package tripagramex.domain.account.repository.mock;

import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockAccountRepository implements AccountRepository {

    public static Map<Long, Account> store = new HashMap<>();
    public static long sequence = 0L;

    @Override
    public Account save(Account account) {
        sequence++;
        Account savedAccount = Account.builder()
                .id(sequence)
                .email(account.getEmail())
                .password(account.getPassword())
                .nickname(account.getNickname())
                .profile(account.getProfile())
                .intro(account.getIntro())
                .role(account.getRole())
                .tempPassword(account.getTempPassword())
                .tempPasswordAppliedAt(account.getTempPasswordAppliedAt())
                .tempPasswordEmailSendAt(account.getTempPasswordEmailSendAt())
                .build();
        store.put(sequence, savedAccount);
        return savedAccount;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return false;
    }
}
