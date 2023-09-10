package tripagramex.domain.board.validation;

public interface BoardValidator {

    void verifyExistsById(Long boardId);

    void verifyUpdateAuthority(Long accountId, Long boardId);
}
