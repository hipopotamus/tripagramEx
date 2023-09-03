package tripagramex.domain.account.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.email.dto.EmailMessageDto;
import tripagramex.domain.email.service.EmailSender;
import tripagramex.domain.email.service.TemplateManager;
import tripagramex.global.intrastructure.PasswordEncoder;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountEmailService {

    private final AccountRepository accountRepository;
    private final EmailSender emailSender;
    private final TemplateManager templateManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendTempPasswordGuid(String email, String backDomain) {
        Account account = accountRepository.findByEmail(email).get();
        account.createTempPassword();

        Context context = getTempPasswordGuidMailContext(account, backDomain);
        String message = templateManager.makeTemplate("mail/simple-link", context);

        EmailMessageDto emailMessageDto = EmailMessageDto.builder()
                .to(account.getEmail())
                .subject("TravelRepo, 임시 비밀번호 발급")
                .message(message)
                .build();

        emailSender.sendEmail(emailMessageDto);

        account.setTempPasswordEmailSendAt();
    }

    @Transactional
    public void applyTempPassword(Long accountId, String tempPassword) {
        Account account = accountRepository.findById(accountId).get();
        String encodedTempPassword = passwordEncoder.encode(tempPassword);
        account.applyTempPassword(encodedTempPassword);
    }

    private Context getTempPasswordGuidMailContext(Account account, String backDomain) {
        Context context = new Context();
        context.setVariable("link", "accountEmail/tempPassword/" + account.getId() +
                "?tempPassword=" + account.getTempPassword());
        context.setVariable("nickname", account.getNickname());
        context.setVariable("linkName", "임시 비밀번호 발급");
        context.setVariable("message", "임시 비밀번호로 변경하려면 링크를 클릭하세요");
        context.setVariable("tempPassword", account.getTempPassword());
        context.setVariable("host", "http://" + backDomain);
        return context;
    }
}
