package tripagramex.domain.likes.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.repository.BoardRepository;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Component
@RequiredArgsConstructor
public class GenericLikeValidator implements LikeValidator {

    private final BoardRepository boardRepository;

    @Override
    public void verifySelfLike(Long accountId, Long boardId) {
        Board board = boardRepository.findWithAccount(boardId).get();
        if (board.getAccount().getId().equals(accountId)) {
            throw new BusinessLogicException(ExceptionCode.SELF_LIKE);
        }
    }
}
