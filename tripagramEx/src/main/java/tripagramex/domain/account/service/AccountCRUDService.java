package tripagramex.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.dto.AccountAddReq;
import tripagramex.domain.account.dto.requiredForAddAccount;
import tripagramex.domain.account.dto.IdDto;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.image.service.ImageService;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountCRUDService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageService imageService;

    @Value("${dir}")
    private String profilePath;

    @Transactional
    public IdDto addAccount(AccountAddReq accountAddReq) {
        verifyDuplicateAccountFactor(accountAddReq);
        Account account = createAccountFromAccountAddReq(accountAddReq);
        Account savedAccount = accountRepository.save(account);

        return new IdDto(savedAccount.getId());
    }

    private Account createAccountFromAccountAddReq(AccountAddReq accountAddReq) {
        requiredForAddAccount requiredForAddAccount = getRequiredForAddAccount(accountAddReq);
        Account account = accountAddReq.toAccount(requiredForAddAccount);
        return account;
    }

    private requiredForAddAccount getRequiredForAddAccount(AccountAddReq accountAddReq) {
        String encodedPassword = bCryptPasswordEncoder.encode(accountAddReq.getPassword());
        String profile = imageService.uploadImage(accountAddReq.getProfile(), profilePath);

        return requiredForAddAccount.builder()
                .encodedPassword(encodedPassword)
                .profile(profile)
                .build();
    }

    private void verifyDuplicateAccountFactor(AccountAddReq accountAddReq) {
        verifyDuplicateEmail(accountAddReq.getEmail());
        verifyDuplicateNickname(accountAddReq.getNickname());
    }

    private void verifyDuplicateEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATION_EMAIL);
        }
    }

    private void verifyDuplicateNickname(String nickname) {
        if (accountRepository.existsByNickname(nickname)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATION_NICKNAME);
        }
    }
}
