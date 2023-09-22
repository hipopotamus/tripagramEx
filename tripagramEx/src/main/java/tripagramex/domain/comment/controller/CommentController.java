package tripagramex.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.validation.AccountValidator;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.validation.BoardValidator;
import tripagramex.domain.comment.dto.CreateRequest;
import tripagramex.domain.comment.dto.UpdateRequest;
import tripagramex.domain.comment.service.CommentCRUDService;
import tripagramex.domain.comment.validation.CommentValidator;
import tripagramex.global.argumentresolver.LoginAccountId;
import tripagramex.global.common.dto.IdDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentCRUDService commentCRUDService;
    private final BoardValidator boardValidator;
    private final AccountValidator accountValidator;
    private final CommentValidator commentValidator;

    @PostMapping
    public ResponseEntity<IdDto> createComment(@LoginAccountId Long loginAccountId,
                                               @RequestBody CreateRequest createRequest) {
        Board board = boardValidator.verifyExistsById(createRequest.getBoardId());
        Account account = accountValidator.verifyExistsById(loginAccountId);

        IdDto idDto = commentCRUDService.createComment(createRequest, board, account);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@LoginAccountId Long loginAccountId, @PathVariable Long commentId,
                                                @RequestBody UpdateRequest updateRequest) {
        commentValidator.verifyUpdateAuthority(loginAccountId);

        commentCRUDService.updateComment(updateRequest, commentId);
        return new ResponseEntity<>("Success Update Comment", HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@LoginAccountId Long loginAccountId, @PathVariable Long commentId) {
        commentValidator.verifyUpdateAuthority(loginAccountId);

        commentCRUDService.deleteComment(commentId);
        return new ResponseEntity<>("Success delete Comment", HttpStatus.OK);
    }
}
