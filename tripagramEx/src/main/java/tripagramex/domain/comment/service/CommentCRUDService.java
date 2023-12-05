package tripagramex.domain.comment.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.comment.dto.*;
import tripagramex.domain.comment.entity.Comment;
import tripagramex.domain.comment.repository.CommentRepository;
import tripagramex.global.common.dto.IdDto;
import tripagramex.global.common.dto.SliceDto;

import java.time.LocalDateTime;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentCRUDService {

    private final CommentRepository commentRepository;

    @Transactional
    public IdDto createComment(CreateRequest createRequest, Board board, Account account) {
        Comment comment = createRequest.toComment(board, account);
        Comment savedComment = commentRepository.save(comment);
        return new IdDto(savedComment.getId());
    }

    @Transactional
    public IdDto createSubComment(CreateSubCommentRequest createSubCommentRequest, Board board, Account account,
                                  Comment comment) {
        Comment subComment = createSubCommentRequest.toComment(board, account, comment);
        Comment saveSubComment = commentRepository.save(subComment);
        return new IdDto(saveSubComment.getId());
    }

    @Transactional
    public void updateComment(UpdateRequest updateRequest, Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.modifyContent(updateRequest.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        if (!comment.getSubComments().isEmpty()) {
            comment.saveContent();
            comment.modifyContent("삭제된 댓글입니다.");
        } else {
            comment.softDelete();
        }
    }

    public SliceDto<ReadResponse> readBoardComments(Long boardId, Long lastCommentId, LocalDateTime lastCommentCreatedAt, Pageable pageable) {

        Slice<Comment> comments;

        if (lastCommentId == null) {
            comments = commentRepository.findByBoard(boardId, pageable);
        } else {
            comments = commentRepository.findByBoardIdWithAccountAndSubCommentsAndParent(boardId, lastCommentId, lastCommentCreatedAt, pageable);
        }

        return new SliceDto<>(comments.map(ReadResponse::of));
    }

    public SliceDto<ReadResponseByAccount> readAccountComments(Long accountId, Long lastCommentId, LocalDateTime lastCommentCreatedAt, Pageable pageable) {

        Slice<Comment> comments;

        if (lastCommentId == null) {
            comments = commentRepository.findByAccount(accountId, pageable);
        } else {
            comments = commentRepository.findByAccountWithAccountAndTargetAccountAndBoard(accountId, lastCommentId, lastCommentCreatedAt, pageable);
        }

        return new SliceDto<>(comments.map(ReadResponseByAccount::of));
    }

}
