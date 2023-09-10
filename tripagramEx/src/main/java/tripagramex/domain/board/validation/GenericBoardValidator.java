package tripagramex.domain.board.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.repository.BoardRepository;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Component
@RequiredArgsConstructor
public class GenericBoardValidator implements BoardValidator {

    private final BoardRepository boardRepository;

    @Override
    public void verifyExistsById(Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND_BOARD);
        }
    }

    @Override
    public void verifyUpdateAuthority(Long accountId, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        if (!accountId.equals(board.getAccount().getId())) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }
    }
}
