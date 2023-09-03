package tripagramex.domain.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import tripagramex.domain.email.dto.EmailMessageDto;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${mode.local:true} or ${mode.server:true}")
public class ServerEmailSender implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailMessageDto emailMessageDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessageDto.getTo());
            mimeMessageHelper.setSubject(emailMessageDto.getSubject());
            mimeMessageHelper.setText(emailMessageDto.getMessage(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessLogicException(ExceptionCode.MAIL_SEND_FAILED);
        }
    }
}
