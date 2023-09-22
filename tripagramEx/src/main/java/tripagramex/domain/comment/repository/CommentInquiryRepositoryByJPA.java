package tripagramex.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.comment.entity.Comment;

public interface CommentInquiryRepositoryByJPA extends CommentInquiryRepository, CommentInquiryRepositoryByQueryDsl,
        JpaRepository<Comment, Long> {
}
