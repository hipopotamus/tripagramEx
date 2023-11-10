package tripagramex.domain.comment.dto;

import lombok.Builder;
import lombok.Data;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
public class ReadResponseByAccount {

    private Long commentId;

    private Long boardId;

    private AccountDto account;

    private String targetAccountNickname;

    private String content;

    private LocalDateTime modifiedAt;

    static public ReadResponseByAccount of(Comment comment) {
        String targetAccountNickname = null;
        Optional<Account> optionalTargetAccount = Optional.ofNullable(comment.getTargetAccount());
        if (optionalTargetAccount.isPresent()) {
            targetAccountNickname = optionalTargetAccount.get().getNickname();
        }
        return ReadResponseByAccount.builder()
                .commentId(comment.getId())
                .boardId(comment.getBoard().getId())
                .account(AccountDto.of(comment.getAccount()))
                .targetAccountNickname(targetAccountNickname)
                .content(comment.getContent())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

}
