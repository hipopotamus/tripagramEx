package tripagramex.domain.comment.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tripagramex.domain.comment.entity.Comment;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    @Query("select comment from Comment comment " +
            "where comment.id = :commentId and comment.deleted = false")
    Optional<Comment> findById(@Param("commentId") Long commentId);

    @Query("select nullif(comment.account.id, :accountId) from Comment comment where comment.id = :commentId")
    Long checkUpdateAuthority(@Param("accountId") Long accountId, @Param("commentId") Long commentId);


    @Query("select case when (comment.parent is null) then 0 else comment.parent.id end from Comment comment " +
            "where comment.id = :commentId")
    Long checkParent(@Param("commentId") Long commentId);

    @EntityGraph(attributePaths = {"account", "subComments", "parent"})
    @Query("select comment from Comment comment " +
            "where comment.id = :commentId and comment.deleted = false ")
    Optional<Comment> findWithAccount(@Param("commentId") Long commentId);
}
