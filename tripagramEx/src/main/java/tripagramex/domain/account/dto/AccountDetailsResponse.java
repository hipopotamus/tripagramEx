package tripagramex.domain.account.dto;

import lombok.Data;
import tripagramex.domain.account.entity.Account;

@Data
public class AccountDetailsResponse {

    private Long id;

    private String email;

    private String nickname;

    private String intro;

    private String profile;

    private Long following;

    private Long follower;

    public static AccountDetailsResponse of(RequiredForFindResponse requiredForFindResponse) {
        AccountDetailsResponse accountDetailsResponse = new AccountDetailsResponse();
        Account account = requiredForFindResponse.getAccount();

        accountDetailsResponse.setId(account.getId());
        accountDetailsResponse.setEmail(account.getEmail());
        accountDetailsResponse.setNickname(account.getNickname());
        accountDetailsResponse.setIntro(account.getIntro());
        accountDetailsResponse.setProfile(account.getProfile());
        accountDetailsResponse.setFollowing(requiredForFindResponse.getFollowing());
        accountDetailsResponse.setFollower(requiredForFindResponse.getFollower());

        return accountDetailsResponse;
    }
}
