package tripagramex.domain.account.dto;

import lombok.Data;
import tripagramex.domain.account.entity.Account;

@Data
public class ReadLoginAccountResponse {

    private Long id;

    private String email;

    private String nickname;

    private String profile;

    public static ReadLoginAccountResponse of(Account account) {
        ReadLoginAccountResponse readLoginAccountResponse = new ReadLoginAccountResponse();

        readLoginAccountResponse.setId(account.getId());
        readLoginAccountResponse.setEmail(account.getEmail());
        readLoginAccountResponse.setNickname(account.getNickname());
        readLoginAccountResponse.setProfile(account.getProfile());

        return readLoginAccountResponse;
    }
}
