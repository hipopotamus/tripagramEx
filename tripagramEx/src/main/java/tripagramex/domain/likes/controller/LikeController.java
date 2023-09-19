package tripagramex.domain.likes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.validation.AccountValidator;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.validation.BoardValidator;
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
}
