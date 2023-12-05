package tripagramex.domain.comment.dto;

import lombok.Builder;
import lombok.Data;
import tripagramex.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Data
@Builder
public class ReadResponseByAccount {

    private Long commentId;

    private Long boardId;

    private AccountDto account;

    private String targetNickname;

    private String content;

    private LocalDateTime modifiedAt;

    static public ReadResponseByAccount of(Comment comment) {
        return ReadResponseByAccount.builder()
                .commentId(comment.getId())
                .boardId(comment.getBoard().getId())
                .account(AccountDto.of(comment.getAccount()))
                .targetNickname(comment.getTargetNickname())
                .content(comment.getContent())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

}
