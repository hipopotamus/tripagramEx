package tripagramex.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.entity.Role;

@Data
public class AccountAddReq {

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
    private MultipartFile profile;

    public Account toAccount(requiredForAddAccount requiredForAddAccount) {

        return Account.builder()
                .email(this.getEmail())
                .password(requiredForAddAccount.getEncodedPassword())
                .nickname(this.getNickname())
                .profile(requiredForAddAccount.getProfile())
                .role(Role.USER)
                .build();
    }

}
