package tripagramex.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.validation.AccountValidator;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.validation.BoardValidator;
import tripagramex.domain.comment.dto.*;
import tripagramex.domain.comment.entity.Comment;
import tripagramex.domain.comment.service.CommentCRUDService;
import tripagramex.domain.comment.validation.CommentValidator;
import tripagramex.global.argumentresolver.LoginAccountId;
import tripagramex.global.common.dto.IdDto;
import tripagramex.global.common.dto.SliceDto;

import java.time.LocalDateTime;

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

    @PostMapping("/subComment")
    public ResponseEntity<IdDto> createSubComment(@LoginAccountId Long loginAccountId,
                                                  @RequestBody CreateSubCommentRequest createSubCommentRequest) {
        Board board = boardValidator.verifyExistsById(createSubCommentRequest.getBoardId());
        Account account = accountValidator.verifyExistsById(loginAccountId);
        Account targetAccount = accountValidator.verifyExistsById(createSubCommentRequest.getTargetAccountId());
        Comment comment = commentValidator.verifyExistsById(createSubCommentRequest.getCommentId());

        IdDto idDto = commentCRUDService.createSubComment(createSubCommentRequest, board, account, targetAccount, comment);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("{commentId}")
    public ResponseEntity<ReadResponse> readComment(@PathVariable Long commentId) {
        ReadResponse readResponse = commentCRUDService.readComment(commentId);
        return new ResponseEntity<>(readResponse, HttpStatus.OK);
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@LoginAccountId Long loginAccountId, @PathVariable Long commentId,
                                                @RequestBody UpdateRequest updateRequest) {
        commentValidator.verifyUpdateAuthority(loginAccountId, commentId);

        commentCRUDService.updateComment(updateRequest, commentId);
        return new ResponseEntity<>("Success Update Comment", HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@LoginAccountId Long loginAccountId, @PathVariable Long commentId) {
        commentValidator.verifyUpdateAuthority(loginAccountId, commentId);

        commentCRUDService.deleteComment(commentId);
        return new ResponseEntity<>("Success delete Comment", HttpStatus.OK);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<SliceDto<ReadResponse>> readBoardComments(@PathVariable Long boardId,
                                                                    @RequestParam(required = false) Long lastCommentId,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime lastCommentCreatedAt,
                                                                    @PageableDefault(size = 5,  sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        SliceDto<ReadResponse> response = commentCRUDService.readBoardComments(boardId, lastCommentId, lastCommentCreatedAt, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/account")
    public ResponseEntity<SliceDto<ReadResponseByAccount>> readAccountComments(@LoginAccountId Long loginAccountId,
                                                                               @RequestParam(required = false) Long lastCommentId,
                                                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime lastCommentCreatedAt,
                                                                               @PageableDefault(size = 5,  sort = {"createdAt", "id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        SliceDto<ReadResponseByAccount> response = commentCRUDService.readAccountComments(loginAccountId, lastCommentId, lastCommentCreatedAt, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
