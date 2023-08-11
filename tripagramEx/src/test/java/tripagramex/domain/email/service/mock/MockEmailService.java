package tripagramex.domain.email.service.mock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import tripagramex.domain.email.dto.EmailMessageDto;
import tripagramex.domain.email.service.EmailService;

@Service
@ConditionalOnProperty(value = "mode.test", havingValue = "true")
public class MockEmailService implements EmailService {

    @Override
    public void sendEmail(EmailMessageDto emailMessageDto) {
    }
}
