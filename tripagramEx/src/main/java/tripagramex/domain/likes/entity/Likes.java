package tripagramex.domain.likes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
