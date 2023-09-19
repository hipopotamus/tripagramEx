package tripagramex.domain.likes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.likes.entity.Likes;

import java.util.Optional;

public interface LikeRepository {

    Likes save(Likes likes);

    Optional<Likes> findByAccountAndBoard(Account account, Board board);

    @Query("select count(likes) > 0 from Likes likes " +
            "where likes.account.id = :accountId and likes.board.id = :boardId and likes.deleted = false")
    boolean existByBothId(@Param("accountId") Long accountId, @Param("boardId") Long boardId);
}
