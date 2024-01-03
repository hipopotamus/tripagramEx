package tripagramex.domain.comment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tripagramex.domain.comment.entity.Comment;
import tripagramex.domain.comment.repository.CommentRepository;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Component
@RequiredArgsConstructor
public class GenericCommentValidator implements CommentValidator {

    private final CommentRepository commentRepository;

    @Override
    public void verifyUpdateAuthority(Long accountId, Long commentId) {
        Long result = commentRepository.checkAuthority(accountId, commentId);
        if (result != null) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }
    }

    @Override
    public Comment verifyExistsById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_COMMENT));
    }
}
