package tripagramex.domain.comment.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.comment.dto.CreateRequest;
import tripagramex.domain.comment.dto.UpdateRequest;
import tripagramex.domain.comment.entity.Comment;
import tripagramex.domain.comment.repository.CommentRepository;
import tripagramex.global.common.dto.IdDto;

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
    public void updateComment(UpdateRequest updateRequest, Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.modifyContent(updateRequest.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        if (!comment.getSubCommentList().isEmpty()) {
            comment.saveContent();
            comment.modifyContent("삭제된 댓글입니다.");
        } else {
            comment.softDelete();
        }
    }
}
