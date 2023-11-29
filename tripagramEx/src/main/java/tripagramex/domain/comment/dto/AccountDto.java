package tripagramex.domain.comment.dto;

import lombok.Builder;
import lombok.Data;
import tripagramex.domain.account.entity.Account;

@Data
@Builder
public class AccountDto {

    private Long accountId;

    private String nickname;

    static public AccountDto of(Account account) {
        return AccountDto.builder()
                .accountId(account.getId())
                .nickname(account.getNickname())
                .build();
    }
}
