package tripagramex.domain.account.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequiredForAddAccount {

    private String encodedPassword;

    private String profile;
}
