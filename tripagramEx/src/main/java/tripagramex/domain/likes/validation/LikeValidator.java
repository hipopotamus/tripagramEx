package tripagramex.domain.likes.validation;

public interface LikeValidator {

    void verifySelfLike(Long accountId, Long boardId);
}
