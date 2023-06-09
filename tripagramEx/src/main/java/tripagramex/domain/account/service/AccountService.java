package tripagramex.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.dto.AccountAddReq;
import tripagramex.domain.account.dto.IdDto;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.image.service.ImageService;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageService imageService;

    @Value("${dir}")
    private String profilePath;

    @Transactional
    public IdDto addAccount(AccountAddReq accountAddReq) {

        verifyDuplicateEmail(accountAddReq.getEmail());
        verifyDuplicateNickname(accountAddReq.getNickname());

        String encodedPassword = bCryptPasswordEncoder.encode(accountAddReq.getPassword());
        String profile = imageService.uploadImage(accountAddReq.getProfile(), profilePath);

        Account account = accountAddReq.to(encodedPassword, profile);
        Account savedAccount = accountRepository.save(account);

        return new IdDto(savedAccount.getId());
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
