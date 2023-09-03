package tripagramex.domain.email.service;

import org.springframework.stereotype.Component;
import tripagramex.domain.email.dto.EmailMessageDto;

@Component
public interface EmailSender {

    void sendEmail(EmailMessageDto emailMessageDto);
}
