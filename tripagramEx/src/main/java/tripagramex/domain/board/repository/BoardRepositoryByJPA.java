package tripagramex.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.board.entity.Board;

public interface BoardRepositoryByJPA extends BoardRepository, BoardRepositoryByQueryDsl, JpaRepository<Board, Long> {
}
