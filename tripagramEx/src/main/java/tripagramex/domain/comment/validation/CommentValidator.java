package tripagramex.domain.comment.validation;

import tripagramex.domain.comment.entity.Comment;

public interface CommentValidator {

    void verifyUpdateAuthority(Long accountId, Long commentId);

    Comment verifyExistsById(Long id);
}
