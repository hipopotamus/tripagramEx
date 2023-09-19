package tripagramex.domain.likes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.validation.AccountValidator;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.validation.BoardValidator;
import tripagramex.domain.likes.dto.CheckLikeResponse;
import tripagramex.domain.likes.dto.CountBoardLikeResponse;
import tripagramex.domain.likes.dto.PostLikeResponse;
import tripagramex.domain.likes.service.LikeService;
import tripagramex.domain.likes.validation.LikeValidator;
import tripagramex.global.argumentresolver.LoginAccountId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final AccountValidator accountValidator;
    private final BoardValidator boardValidator;
    private final LikeValidator likeValidator;

    @PostMapping("/{boardId}")
    public ResponseEntity<PostLikeResponse> postLike(@LoginAccountId Long loginAccountId, @PathVariable Long boardId) {
        Account account = accountValidator.verifyExistsById(loginAccountId);
        Board board = boardValidator.verifyExistsById(boardId);
        likeValidator.verifySelfLike(loginAccountId, boardId);

        PostLikeResponse postLikeResponse = likeService.postLike(account, board);
        return new ResponseEntity<>(postLikeResponse, HttpStatus.OK);
    }

    @GetMapping("/check/{boardId}")
    public ResponseEntity<CheckLikeResponse> checkLike(@LoginAccountId Long loginAccountId, @PathVariable Long boardId) {
        CheckLikeResponse checkLikeResponse = likeService.checkLike(loginAccountId, boardId);
        return new ResponseEntity<>(checkLikeResponse, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<CountBoardLikeResponse> countBoardLike(@PathVariable Long boardId) {
        CountBoardLikeResponse countBoardLikeResponse = likeService.countBoardLike(boardId);
        return new ResponseEntity<>(countBoardLikeResponse, HttpStatus.OK);
    }
}
