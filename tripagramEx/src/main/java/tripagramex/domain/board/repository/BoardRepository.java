package tripagramex.domain.board.repository;

import org.springframework.stereotype.Repository;
import tripagramex.domain.board.entity.Board;

@Repository
public interface BoardRepository {

    Board save(Board board);
}
