package tripagramex.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tripagramex.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadResponse {

    private Long commentId;

    private AccountDto account;

    private String content;

    private LocalDateTime modifiedAt;

    private int subCommentSize = 0;

    public static ReadResponse of(Comment comment) {
        return ReadResponse.builder()
                .commentId(comment.getId())
                .account(AccountDto.of(comment.getAccount()))
                .content(comment.getContent())
                .modifiedAt(comment.getModifiedAt())
                .subCommentSize(comment.getSubComments().size())
                .build();
    }
}
