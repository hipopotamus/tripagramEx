package tripagramex.domain.comment.dto;

import lombok.Data;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.comment.entity.Comment;

@Data
public class CreateRequest {

    private Long boardId;

    private String content;

    public Comment toComment(Board board, Account account) {
        return Comment.builder()
                .content(content)
                .board(board)
                .account(account)
                .build();
    }
}
