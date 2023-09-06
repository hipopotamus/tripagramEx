package tripagramex.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripagramex.domain.account.validation.AccountValidator;
import tripagramex.domain.follow.dto.PostFollowResponse;
import tripagramex.domain.follow.service.FollowService;
import tripagramex.domain.follow.validation.FollowValidator;
import tripagramex.global.argumentresolver.LoginAccountId;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final FollowValidator followValidator;
    private final AccountValidator accountValidator;

    @PostMapping("/{followingId}")
    public ResponseEntity<PostFollowResponse> postFollow(@LoginAccountId Long loginAccountId, @PathVariable Long followingId) {
        accountValidator.verifyExistsById(loginAccountId);
        accountValidator.verifyExistsById(followingId);
        followValidator.verifySelfFollow(loginAccountId, followingId);

        PostFollowResponse postFollowResponse = followService.postFollow(loginAccountId, followingId);

        return new ResponseEntity<>(postFollowResponse, HttpStatus.OK);
    }
}
