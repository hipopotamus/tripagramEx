package tripagramex.domain.account.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tripagramex.domain.account.dto.CreateRequest;

@Component
@RequiredArgsConstructor
public class CreateRequestValidator implements Validator {

    private final AccountValidator accountValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CreateRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateRequest createRequest = (CreateRequest) target;

        accountValidator.verifyDuplicateEmail(createRequest.getEmail());
        accountValidator.verifyDuplicateNickname(createRequest.getNickname());
    }
}
