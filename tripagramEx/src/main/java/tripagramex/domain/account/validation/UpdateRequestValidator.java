package tripagramex.domain.account.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tripagramex.domain.account.dto.UpdateRequest;

@Component
@RequiredArgsConstructor
public class UpdateRequestValidator implements Validator {

    private final AccountValidator accountValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UpdateRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateRequest updateRequest = (UpdateRequest) target;

        if (updateRequest.getNickname() != null) {
            accountValidator.verifyDuplicateNickname(updateRequest.getNickname());
        }
    }
}
