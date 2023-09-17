package tripagramex.domain.follow.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.mock.MockAccountRepository;
import tripagramex.domain.follow.dto.CheckFollowResponse;
import tripagramex.domain.follow.dto.PostFollowResponse;
import tripagramex.domain.follow.entity.Follow;
import tripagramex.domain.follow.repository.mock.MockFollowRepository;
import tripagramex.global.common.enums.Status;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FollowServiceTest {

    private final MockAccountRepository accountRepository = new MockAccountRepository();
    private final MockFollowRepository followRepository = new MockFollowRepository();
    private final FollowService followService = FollowService.builder()
            .accountRepository(accountRepository)
            .followRepository(followRepository)
            .build();

    @AfterEach
    void after() {
        accountRepository.initiate();
        followRepository.initiate();
    }

    @Test
    @DisplayName("팔로우 시행_성공")
    public void postFollow_Success() {
        //given
        Long followerId = 10001L;
        Long followingId = 10002L;

        //when
        PostFollowResponse postFollowResponse = followService.postFollow(followerId, followingId);

        //then
        Account follower = accountRepository.findById(followerId).get();
        Account following = accountRepository.findById(followingId).get();
        Optional<Follow> optionalFollow = followRepository.findByFollowerAndFollowing(follower, following);

        assertThat(optionalFollow.isPresent()).isTrue();
        assertThat(postFollowResponse.getStatus()).isEqualTo(Status.SUCCESS);
    }

    @Test
    @DisplayName("팔로우 취소_성공")
    public void cancelFollow_Success() {
        //given
        Long followerId = 10001L;
        Long followingId = 10003L;

        //when
        PostFollowResponse postFollowResponse = followService.postFollow(followerId, followingId);

        //then
        Account follower = accountRepository.findById(followerId).get();
        Account following = accountRepository.findById(followingId).get();
        Optional<Follow> optionalFollow = followRepository.findByFollowerAndFollowing(follower, following);

        assertThat(optionalFollow.isPresent()).isFalse();
        assertThat(postFollowResponse.getStatus()).isEqualTo(Status.CANCEL);
    }

    @Test
    @DisplayName("팔로우 반복 시행_팔로우 시행 성공")
    public void repeatFollow_postFollow_Success() {
        //given
        Long followerId = 10001L;
        Long followingId = 10002L;

        //when
        followService.postFollow(followerId, followingId);
        followService.postFollow(followerId, followingId);
        followService.postFollow(followerId, followingId);
        followService.postFollow(followerId, followingId);
        PostFollowResponse postFollowResponse = followService.postFollow(followerId, followingId);

        //then
        Account follower = accountRepository.findById(followerId).get();
        Account following = accountRepository.findById(followingId).get();
        Optional<Follow> optionalFollow = followRepository.findByFollowerAndFollowing(follower, following);

        assertThat(optionalFollow.isPresent()).isTrue();
        assertThat(postFollowResponse.getStatus()).isEqualTo(Status.SUCCESS);
    }

    @Test
    @DisplayName("팔로우 여부 확인_성공")
    public void checkFollow_Success() {
        //given
        Long followerId = 10001L;
        Long followingId = 10002L;

        //when
        CheckFollowResponse checkFollowResponse = followService.checkFollow(followerId, followingId);

        //then
        assertThat(checkFollowResponse.isFollow()).isFalse();
    }
}