package tripagramex.domain.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @EntityGraph(attributePaths = {"account"})
    @Query("select comment from Comment comment " +
            "left join fetch comment.subComments subcomment where comment.board.id = :boardId " +
            "and (subcomment.deleted = false or subcomment.deleted = null) " +
            "and comment.target.id = 0L " +
            "and comment.deleted = false")
    Slice<Comment> findByBoard(@Param("boardId") Long boardId, Pageable pageable);

    @EntityGraph(attributePaths = {"account", "board"})
    @Query("select comment from Comment comment " +
            "where comment.account.id = :accountId and comment.deleted = false")
    Slice<Comment> findByAccount(@Param("accountId") Long accountId, Pageable pageable);

    @EntityGraph(attributePaths = {"account"})
    @Query("select comment from Comment comment " +
            "left join fetch comment.subComments subcomment where comment.board.id = :boardId " +
            "and (subcomment.deleted = false or subcomment.deleted = null) " +
            "and comment.target.id = 0L " +
            "and comment.id < :lastCommentId")
    Slice<Comment> findByBoardIdWithAccountAndSubCommentsAndParent(@Param("boardId") Long boardId,
                                                                   @Param("lastCommentId") Long lastCommentId,
                                                                   Pageable pageable);

    @EntityGraph(attributePaths = {"account", "board"})
    @Query("select comment from Comment comment where comment.account.id = :accountId and comment.deleted = false " +
            "and comment.id < :lastCommentId")
    Slice<Comment> findByAccountWithAccountAndTargetAccountAndBoard(@Param("accountId") Long accountId,
                                                                    @Param("lastCommentId") Long lastCommentId,
                                                                    Pageable pageable);
}
