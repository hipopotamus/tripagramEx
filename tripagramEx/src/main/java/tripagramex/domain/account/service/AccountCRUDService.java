package tripagramex.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.dto.*;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.follow.repository.FollowRepository;
import tripagramex.domain.image.service.ImageService;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountCRUDService {

    private final AccountRepository accountRepository;
    private final FollowRepository followRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageService imageService;

    @Value("${dir}")
    private String profilePath;

    @Transactional
    public IdDto addAccount(AccountAddRequest accountAddRequest) {
        verifyDuplicateAccountFactor(accountAddRequest);
        Account account = createAccountFromAccountAddReq(accountAddRequest);
        Account savedAccount = accountRepository.save(account);

        return new IdDto(savedAccount.getId());
    }

    public AccountDetailsResponse findAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        return getAccountDetailsResponse(account);
    }

    private Account createAccountFromAccountAddReq(AccountAddRequest accountAddRequest) {
        RequiredForAddAccount requiredForAddAccount = getRequiredForAddAccount(accountAddRequest);

        return accountAddRequest.toAccount(requiredForAddAccount);
    }

    private RequiredForAddAccount getRequiredForAddAccount(AccountAddRequest accountAddRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(accountAddRequest.getPassword());
        String profile = imageService.uploadImage(accountAddRequest.getProfile(), profilePath);

        return RequiredForAddAccount.builder()
                .encodedPassword(encodedPassword)
                .profile(profile)
                .build();
    }

    private AccountDetailsResponse getAccountDetailsResponse(Account account) {
        RequiredForFindAccount requiredForFindAccount = getRequiredForFindAccount(account);

        return AccountDetailsResponse.of(requiredForFindAccount);
    }

    private RequiredForFindAccount getRequiredForFindAccount(Account account) {
        Long following = followRepository.countByFollower(account);
        Long follower = followRepository.countByFollowing(account);

        return RequiredForFindAccount.builder()
                .account(account)
                .following(following)
                .follower(follower)
                .build();
    }

    private void verifyDuplicateAccountFactor(AccountAddRequest accountAddRequest) {
        verifyDuplicateEmail(accountAddRequest.getEmail());
        verifyDuplicateNickname(accountAddRequest.getNickname());
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
