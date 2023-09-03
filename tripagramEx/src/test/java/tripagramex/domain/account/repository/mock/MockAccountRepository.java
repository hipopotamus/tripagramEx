package tripagramex.domain.account.repository.mock;

import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockAccountRepository implements AccountRepository {

    public static List<Account> store = new ArrayList<>();
    public static long sequence = 0L;

    public MockAccountRepository() {
        initSample();
    }

    private void initSample() {
        saveSampleOne();
    }

    private void saveSampleOne() {
        Account account = Account.builder()
                .id(1L)
                .email("test1@test.com")
                .password("[Encode]test1Password")
                .nickname("test1Nickname")
                .profile("test1Profile")
                .intro("test1Intro")
                .build();
        save(account);
    }

    @Override
    public Account save(Account account) {
        Long id = account.getId();
        if (id == null || id == 0L) {
            increaseSequence();
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
            store.add(savedAccount);
            return savedAccount;
        } else {
            store.removeIf(item -> item.getId().equals(id));
            store.add(account);
            return account;
        }
    }

    @Override
    public Optional<Account> findById(Long id) {
        return store.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return true;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return store.stream()
                .filter(account -> account.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return false;
    }


    public void clearAll() {
        store.clear();
        initSample();
    }

    private void increaseSequence() {
        while (true) {
            if (!existAccountById(++sequence)) {
                break;
            }
        }
    }

    private boolean existAccountById(long id) {
        Optional<Account> OptionalAccount = store.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
        return OptionalAccount.isPresent();
    }
}
