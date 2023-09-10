package tripagramex.domain.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tripagramex.domain.board.entity.Board;

import java.util.Optional;

@Repository
public interface BoardRepository {

    Board save(Board board);

    @Query("select board from Board board " +
            "where board.id = :boardId and board.deleted = false")
    Optional<Board> findById(@Param("boardId") Long boardId);

    @EntityGraph(attributePaths = {"account"})
    @Query("select board from Board board " +
            "where board.id = :boardId and board.deleted = false")
    Optional<Board> findWithAccount(@Param("boardId") Long boardId);

    @Query("select count(board) > 0 from Board board " +
            "where board.id = :boardId and board.deleted = false")
    boolean existsById(@Param("boardId") Long boardId);
}
