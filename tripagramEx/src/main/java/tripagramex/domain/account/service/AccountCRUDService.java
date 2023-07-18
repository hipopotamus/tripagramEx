package tripagramex.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.dto.*;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.follow.repository.FollowRepository;
import tripagramex.domain.image.service.ImageService;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;
import tripagramex.global.intrastructure.PasswordEncoder;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountCRUDService {

    private final AccountRepository accountRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Value("${dir}")
    private String profilePath;

    @Transactional
    public IdDto create(CreateRequest createRequest) {
        verifyDuplicateForCreate(createRequest);
        Account account = getAccountFromRequest(createRequest);
        Account savedAccount = accountRepository.save(account);

        return new IdDto(savedAccount.getId());
    }

    public ReadResponse read(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        return getReadResponse(account);
    }

    public ReadLoginAccountResponse readLoginAccount(Long loginAccountId) {
        Account account = accountRepository.findById(loginAccountId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        return ReadLoginAccountResponse.of(account);
    }

    public IdDto update(Long loginAccountId, UpdateRequest updateRequest) {
        if (updateRequest.getNickname() != null) {
            verifyDuplicateNickname(updateRequest.getNickname());
        }

        Account modifyAccount = getModifyAccount(updateRequest);
        Account account = accountRepository.findById(loginAccountId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));
        account.modify(modifyAccount);

        return new IdDto(account.getId());
    }

    private Account getModifyAccount(UpdateRequest updateRequest) {
        String encodedPassword = null;
        if (updateRequest.getPassword() != null) {
            encodedPassword = passwordEncoder.encode(updateRequest.getPassword());
        }
        return updateRequest.toAccount(encodedPassword);
    }

    private Account getAccountFromRequest(CreateRequest createRequest) {
        String encodedPassword = passwordEncoder.encode(createRequest.getPassword());
        String profile = imageService.upload(createRequest.getProfile(), profilePath);

        return createRequest.toAccount(encodedPassword, profile);
    }

    private ReadResponse getReadResponse(Account account) {
        Long following = followRepository.countByFollower(account);
        Long follower = followRepository.countByFollowing(account);

        return ReadResponse.of(account, following, follower);
    }

    private void verifyDuplicateForCreate(CreateRequest createRequest) {
        verifyDuplicateEmail(createRequest.getEmail());
        verifyDuplicateNickname(createRequest.getNickname());
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
