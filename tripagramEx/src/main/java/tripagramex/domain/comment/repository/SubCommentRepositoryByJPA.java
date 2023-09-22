package tripagramex.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.comment.entity.SubComment;

public interface SubCommentRepositoryByJPA extends SubCommentRepository, JpaRepository<SubComment, Long> {
}
