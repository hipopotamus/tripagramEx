package tripagramex.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.entity.Role;

@Data
@Builder
public class CreateRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;

    @NotBlank
    @Length(min = 2, max = 20)
    private String nickname;

    @NotNull
    private String profile;

    public Account toAccount(String encodedPassword) {
        return Account.builder()
                .email(this.getEmail())
                .password(encodedPassword)
                .nickname(this.getNickname())
                .profile(this.profile)
                .role(Role.USER)
                .build();
    }

}
