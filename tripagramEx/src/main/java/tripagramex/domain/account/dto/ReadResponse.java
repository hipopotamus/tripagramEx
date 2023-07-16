package tripagramex.domain.account.dto;

import lombok.Data;
import tripagramex.domain.account.entity.Account;

@Data
public class ReadResponse {

    private Long id;

    private String email;

    private String nickname;

    private String intro;

    private String profile;

    private Long following;

    private Long follower;

    public static ReadResponse of(Account account, Long following, Long follower) {
        ReadResponse readResponse = new ReadResponse();

        readResponse.setId(account.getId());
        readResponse.setEmail(account.getEmail());
        readResponse.setNickname(account.getNickname());
        readResponse.setIntro(account.getIntro());
        readResponse.setProfile(account.getProfile());
        readResponse.setFollowing(following);
        readResponse.setFollower(follower);

        return readResponse;
    }
}
