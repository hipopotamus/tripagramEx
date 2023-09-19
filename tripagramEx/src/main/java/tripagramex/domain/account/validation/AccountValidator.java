package tripagramex.domain.account.validation;

import tripagramex.domain.account.entity.Account;

public interface AccountValidator {

    void verifyDuplicateEmail(String email);

    void verifyDuplicateNickname(String nickname);

    Account verifyExistsById(Long accountId);

    void verifyExistsByEmail(String email);

    void verifySendTempPasswordEmailAt(String email);

    void verifyApplyTempPasswordAt(Long accountId);
}
