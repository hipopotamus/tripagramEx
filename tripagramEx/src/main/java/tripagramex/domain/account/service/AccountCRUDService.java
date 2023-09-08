package tripagramex.domain.account.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.dto.*;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.follow.repository.FollowRepository;
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
        Account account = getAccountForCreate(createRequest);
        Account savedAccount = accountRepository.save(account);

        return new IdDto(savedAccount.getId());
    }

    public ReadResponse read(Long accountId) {
        Account account = accountRepository.findById(accountId).get();

        Long following = followRepository.countFollowing(account);
        Long follower = followRepository.countFollower(account);

        return ReadResponse.of(account, following, follower);
    }

    public ReadLoginAccountResponse readLoginAccount(Long loginAccountId) {
        Account account = accountRepository.findById(loginAccountId).get();

        return ReadLoginAccountResponse.of(account);
    }

    @Transactional
    public IdDto update(Long loginAccountId, UpdateRequest updateRequest) {
        Account modifyAccountParameter = getAccountForUpdate(updateRequest);
        Account account = accountRepository.findById(loginAccountId).get();
        account.modify(modifyAccountParameter);

        return new IdDto(account.getId());
    }

    @Transactional
    public void delete(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.softDelete();
    }

    private Account getAccountForCreate(CreateRequest createRequest) {
        String encodedPassword = passwordEncoder.encode(createRequest.getPassword());

        return createRequest.toAccount(encodedPassword);
    }

    private Account getAccountForUpdate(UpdateRequest updateRequest) {
        String encodedPassword = null;
        if (updateRequest.getPassword() != null) {
            encodedPassword = passwordEncoder.encode(updateRequest.getPassword());
        }
        return updateRequest.toAccount(encodedPassword);
    }
}

