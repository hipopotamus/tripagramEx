package tripagramex.domain.account.validation;

public interface AccountValidator {

    void verifyDuplicateEmail(String email);

    void verifyDuplicateNickname(String nickname);

    void verifyExistsById(Long id);
}
