package tripagramex.domain.comment.dto;

import lombok.Builder;
import lombok.Data;
import tripagramex.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Data
@Builder
public class SubCommentDto {

    private Long commentId;

    private AccountDto account;

    private AccountDto targetAccount;

    private String content;

    private LocalDateTime modifiedAt;

    static public SubCommentDto of(Comment comment) {
        return SubCommentDto.builder()
                .commentId(comment.getId())
                .account(AccountDto.of(comment.getAccount()))
                .targetAccount(AccountDto.of(comment.getTargetAccount()))
                .content(comment.getContent())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
