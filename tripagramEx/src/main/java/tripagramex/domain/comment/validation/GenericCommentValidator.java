package tripagramex.domain.comment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tripagramex.domain.comment.repository.CommentRepository;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Component
@RequiredArgsConstructor
public class GenericCommentValidator implements CommentValidator {

    private final CommentRepository commentRepository;

    @Override
    public void verifyUpdateAuthority(Long accountId) {
        Long result = commentRepository.checkUpdateAuthority(accountId);
        if (result != null) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }
    }
}
