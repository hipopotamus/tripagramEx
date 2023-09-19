package tripagramex.domain.likes.repository;

import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.likes.entity.Likes;

import java.util.Optional;

public interface LikeRepository {

    Likes save(Likes likes);

    Optional<Likes> findByAccountAndBoard(Account account, Board board);
}
