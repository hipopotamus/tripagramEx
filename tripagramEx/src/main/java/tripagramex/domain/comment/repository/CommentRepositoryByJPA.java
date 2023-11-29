package tripagramex.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.comment.entity.Comment;

public interface CommentRepositoryByJPA extends CommentRepository, JpaRepository<Comment, Long> {
}
