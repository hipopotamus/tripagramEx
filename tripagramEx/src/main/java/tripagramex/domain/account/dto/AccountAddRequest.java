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
public class AccountAddRequest {

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

    public Account toAccount(RequiredForAddResponse requiredForAddResponse) {
        return Account.builder()
                .email(this.getEmail())
                .password(requiredForAddResponse.getEncodedPassword())
                .nickname(this.getNickname())
                .profile(requiredForAddResponse.getProfile())
                .role(Role.USER)
                .build();
    }

}
