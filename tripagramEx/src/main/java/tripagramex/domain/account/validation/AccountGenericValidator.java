package tripagramex.domain.account.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Component
@RequiredArgsConstructor
public class AccountGenericValidator implements AccountValidator {

    private final AccountRepository accountRepository;

    @Override
    public void verifyDuplicateEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATION_EMAIL);
        }
    }

    @Override
    public void verifyDuplicateNickname(String nickname) {
        if (accountRepository.existsByNickname(nickname)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATION_NICKNAME);
        }
    }

    @Override
    public void verifyExistsById(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT);
        }
    }

    @Override
    public void verifyExistsByEmail(String email) {
        if (!accountRepository.existsByEmail(email)) {
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT);
        }
    }

    @Override
    public void verifySendTempPasswordEmailAt(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        if (!account.canSendTempPasswordGuid()) {
            throw new BusinessLogicException(ExceptionCode.TEMP_PASSWORD_DELAY);
        }

    }

    @Override
    public void verifyApplyTempPasswordAt(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        if (!account.canApplyTempPassword()) {
            throw new BusinessLogicException(ExceptionCode.TEMP_PASSWORD_DELAY);
        }
    }
}
