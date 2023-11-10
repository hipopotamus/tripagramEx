package tripagramex.domain.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tripagramex.domain.comment.entity.Comment;

import java.time.LocalDateTime;
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
    Optional<Comment> findWithAccountAndSubCommentsAndParent(@Param("commentId") Long commentId);

    @EntityGraph(attributePaths = {"account", "subComments", "parent"})
    Slice<Comment> findAllByBoard_Id(Long boardId, Pageable pageable);

    @EntityGraph(attributePaths = {"account", "subComments", "parent"})
    Slice<Comment> findAllByAccount_Id(Long accountId, Pageable pageable);

    @EntityGraph(attributePaths = {"account", "subComments", "parent"})
    @Query("select comment from Comment comment where comment.board.id = :boardId " +
            "and (comment.createdAt < :lastCommentCreatedAt or (comment.createdAt = :lastCommentCreatedAt and comment.id < :lastCommentId))")
    Slice<Comment> findByBoardIdWithAccountAndSubCommentsAndParent(@Param("boardId") Long boardId,
                                                                   @Param("lastCommentId") Long lastCommentId,
                                                                   @Param("lastCommentCreatedAt") LocalDateTime lastCommentCreatedAt,
                                                                   Pageable pageable);
    @EntityGraph(attributePaths = {"account", "targetAccount", "board"})
    @Query("select comment from Comment comment where comment.account.id = :accountId and comment.deleted = false " +
            "and (comment.createdAt < :lastCommentCreatedAt or (comment.createdAt = :lastCommentCreatedAt and comment.id < :lastCommentId))")
    Slice<Comment> findByAccountWithAccountAndTargetAccountAndBoard(@Param("accountId") Long accountId,
                                                                    @Param("lastCommentId") Long lastCommentId,
                                                                    @Param("lastCommentCreatedAt") LocalDateTime lastCommentCreatedAt,
                                                                    Pageable pageable);
}
