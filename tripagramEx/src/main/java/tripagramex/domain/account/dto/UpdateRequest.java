package tripagramex.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import tripagramex.domain.account.entity.Account;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {

    @Length(min = 8, max = 30)
    private String password;

    @Length(min = 2, max = 20)
    private String nickname;

    private String profile;

    private String intro;

    public Account toAccount(String encodedPassword) {
        return Account.builder()
                .password(encodedPassword)
                .nickname(this.nickname)
                .profile(this.profile)
                .intro(this.intro)
                .build();
    }
}
