package tripagramex.domain.comment.dto;

import lombok.Data;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.comment.entity.Comment;

@Data
public class CreateSubCommentRequest {

    private Long boardId;

    private Long commentId;

    private Long targetAccountId;

    private String content;

    public Comment toComment(Board board, Account account, Account targetAccount, Comment comment) {
        return Comment.builder()
                .content(content)
                .board(board)
                .account(account)
                .targetAccount(targetAccount)
                .parent(comment)
                .build();
    }
}
