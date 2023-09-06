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
        initiate();
    }

    private void saveSample(Long number) {
        for (long i = 1L; i <= number; i++) {
            Account account = Account.builder()
                    .id(i)
                    .email("test" + i +"@test.com")
                    .password("[Encode]test" + i + "Password")
                    .nickname("test" + i + "Nickname")
                    .profile("test" + i + "Profile")
                    .intro("test" + i + "Intro")
                    .build();
            save(account);
        }
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
                .filter(account -> (account.getId().equals(id) && !account.isDeleted()))
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return true;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return store.stream()
                .filter(account -> (account.getEmail().equals(email) && !account.isDeleted()))
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


    public void initiate() {
        store.clear();
        sequence = 0L;
        initSample();
    }

    private void initSample() {
        saveSample(3L);
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
                .filter(account -> (account.getId().equals(id) && !account.isDeleted()))
                .findFirst();
        return OptionalAccount.isPresent();
    }
}
