package tripagramex.domain.account.dto;

import lombok.Data;
import tripagramex.domain.account.entity.Account;

@Data
public class LoginAccountDetailsResponse {

    private Long id;

    private String email;

    private String nickname;

    private String profile;

    public static LoginAccountDetailsResponse of(RequiredForFindResponse requiredForFindResponse) {
        LoginAccountDetailsResponse loginAccountDetailsResponse = new LoginAccountDetailsResponse();
        Account account = requiredForFindResponse.getAccount();

        loginAccountDetailsResponse.setId(account.getId());
        loginAccountDetailsResponse.setEmail(account.getEmail());
        loginAccountDetailsResponse.setNickname(account.getNickname());
        loginAccountDetailsResponse.setProfile(account.getProfile());

        return loginAccountDetailsResponse;
    }
}
