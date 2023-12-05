package tripagramex.domain.comment.dto;

import lombok.Data;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.comment.entity.Comment;

@Data
public class CreateSubCommentRequest {

    private Long boardId;

    private Long commentId;

    private Long targetId;

    private String targetNickname;

    private String content;

    public Comment toComment(Board board, Account account, Comment comment) {
        return Comment.builder()
                .content(content)
                .board(board)
                .account(account)
                .targetId(targetId)
                .targetNickname(targetNickname)
                .parent(comment)
                .build();
    }
}
