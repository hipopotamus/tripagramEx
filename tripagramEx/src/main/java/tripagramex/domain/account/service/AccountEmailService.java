package tripagramex.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.email.dto.EmailMessageDto;
import tripagramex.domain.email.service.EmailService;
import tripagramex.global.intrastructure.PasswordEncoder;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountEmailService {

    @Value("${domain.back}")
    private String backDomain;

    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendTempPasswordGuid(String email) {
        Account account = accountRepository.findByEmail(email).get();
        account.createTempPassword();

        Context context = getTempPasswordGuidMailContext(account);
        String message = templateEngine.process("mail/simple-link", context);

        EmailMessageDto emailMessageDto = EmailMessageDto.builder()
                .to(account.getEmail())
                .subject("TravelRepo, 임시 비밀번호 발급")
                .message(message)
                .build();

        emailService.sendEmail(emailMessageDto);

        account.setTempPasswordEmailSendAt();
    }

    private Context getTempPasswordGuidMailContext(Account account) {
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
