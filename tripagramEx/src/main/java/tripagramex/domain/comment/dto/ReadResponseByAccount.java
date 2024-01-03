package tripagramex.domain.comment.dto;

import lombok.Builder;
import lombok.Data;
import tripagramex.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Data
@Builder
public class ReadResponseByAccount {

    private Long commentId;

    private BoardDto board;

    private AccountDto target;

    private String content;

    private LocalDateTime modifiedAt;

    static public ReadResponseByAccount of(Comment comment) {
        return ReadResponseByAccount.builder()
                .commentId(comment.getId())
                .board(BoardDto.of(comment.getBoard()))
                .target(AccountDto.of(comment.getTarget()))
                .content(comment.getContent())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

}
