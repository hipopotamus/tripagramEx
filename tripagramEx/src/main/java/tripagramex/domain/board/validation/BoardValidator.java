package tripagramex.domain.board.validation;

import tripagramex.domain.board.entity.Board;

public interface BoardValidator {

    Board verifyExistsById(Long boardId);

    void verifyUpdateAuthority(Long accountId, Long boardId);
}
