package tripagramex.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.entity.Role;
import tripagramex.domain.account.repository.AccountRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void testAccountPost(String email, String password) {

        String encode = bCryptPasswordEncoder.encode(password);

        Account account = Account.builder()
                .email(email)
                .password(encode)
                .role(Role.USER)
                .build();

        accountRepository.save(account);
    }
}
