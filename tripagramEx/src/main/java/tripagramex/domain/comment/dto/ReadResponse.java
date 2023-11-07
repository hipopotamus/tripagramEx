package tripagramex.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tripagramex.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadResponse {

    private Long commentId;

    private AccountDto account;

    private String content;

    private List<SubCommentDto> subComments = new ArrayList<>();

    private LocalDateTime modifiedAt;

    public static ReadResponse of(Comment comment) {

        List<SubCommentDto> subComments = comment.getSubComments().stream()
                .map(SubCommentDto::of)
                .toList();

        return ReadResponse.builder()
                .commentId(comment.getId())
                .account(AccountDto.of(comment.getAccount()))
                .content(comment.getContent())
                .subComments(subComments)
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
