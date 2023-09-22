package tripagramex.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.comment.entity.SubComment;

public interface SubCommentInquiryRepositoryByJPA extends SubCommentInquiryRepository,
        SubCommentInquiryRepositoryByQueryDsl, JpaRepository<SubComment, Long> {
}
