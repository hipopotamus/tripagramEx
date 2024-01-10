package tripagramex.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.comment.entity.Comment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubCommentRequest {

    private Long boardId;

    private Long commentId;

    private Long targetId;

    private String content;

    public Comment toComment(Board board, Account account, Comment comment, Account target) {
        return Comment.builder()
                .content(content)
                .board(board)
                .account(account)
                .target(target)
                .parent(comment)
                .build();
    }
}
