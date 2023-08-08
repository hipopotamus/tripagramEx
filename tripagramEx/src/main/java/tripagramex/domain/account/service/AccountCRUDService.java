package tripagramex.domain.account.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.dto.*;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.follow.repository.FollowRepository;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;
import tripagramex.global.intrastructure.PasswordEncoder;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountCRUDService {

    private final AccountRepository accountRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public IdDto create(CreateRequest createRequest) {
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

    @Transactional
    public IdDto update(Long loginAccountId, UpdateRequest updateRequest) {
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

        return createRequest.toAccount(encodedPassword);
    }

    private ReadResponse getReadResponse(Account account) {
        Long following = followRepository.countByFollower(account);
        Long follower = followRepository.countByFollowing(account);

        return ReadResponse.of(account, following, follower);
    }
}

