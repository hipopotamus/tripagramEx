package tripagramex.domain.comment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tripagramex.domain.comment.entity.Comment;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    @Query("select comment from Comment comment " +
            "where comment.id = :commentId and comment.deleted = false")
    Optional<Comment> findById(@Param("commentId") Long commentId);

    @Query("select nullif(comment.account.id, :accountId) from Comment comment")
    Long checkUpdateAuthority(@Param("accountId") Long accountId);
}
