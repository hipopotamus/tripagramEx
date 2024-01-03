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
public class ReadSubCommentResponse {

    private Long subCommentId;

    private AccountDto account;

    private AccountDto target;

    private String contents;

    private LocalDateTime modifiedAt;

    public static ReadSubCommentResponse of(Comment subComment) {

        return ReadSubCommentResponse.builder()
                .subCommentId(subComment.getId())
                .account(AccountDto.of(subComment.getAccount()))
                .target(AccountDto.of(subComment.getTarget()))
                .contents(subComment.getContent())
                .modifiedAt(subComment.getModifiedAt())
                .build();
    }
}
