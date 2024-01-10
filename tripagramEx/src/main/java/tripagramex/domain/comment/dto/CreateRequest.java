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
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequest {

    private Long boardId;

    private String content;

    public Comment toComment(Board board, Account account) {
        return Comment.builder()
                .content(content)
                .board(board)
                .account(account)
                .target(Account.builder()
                        .id(0L)
                        .build())
                .parent(Comment.builder()
                        .id(0L)
                        .build())
                .build();
    }
}
