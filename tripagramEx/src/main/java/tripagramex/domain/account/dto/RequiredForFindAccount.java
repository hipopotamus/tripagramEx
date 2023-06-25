package tripagramex.domain.account.dto;

import lombok.Builder;
import lombok.Data;
import tripagramex.domain.account.entity.Account;

@Data
@Builder
public class RequiredForFindAccount {
    private Account account;

    private Long following;

    private Long follower;
}
