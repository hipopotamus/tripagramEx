package tripagramex.domain.follow.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.account.repository.AccountRepository;
import tripagramex.domain.follow.dto.CheckFollowResponse;
import tripagramex.domain.follow.dto.PostFollowResponse;
import tripagramex.domain.follow.entity.Follow;
import tripagramex.domain.follow.repository.FollowRepository;
import tripagramex.global.common.enums.Status;

import java.util.Optional;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final AccountRepository accountRepository;
    private final FollowRepository followRepository;

    @Transactional
    public PostFollowResponse postFollow(Long followerId, Long followingId) {

        Account follower = accountRepository.findById(followerId).get();
        Account following = accountRepository.findById(followingId).get();

        Optional<Follow> optionalFollow = followRepository.findByFollowerAndFollowing(follower, following);
        if (optionalFollow.isEmpty()) {
            Follow follow = Follow.builder()
                    .follower(follower)
                    .following(following)
                    .build();

            followRepository.save(follow);

            return new PostFollowResponse(Status.SUCCESS);
        }

        Follow follow = optionalFollow.get();
        if (follow.isDeleted()) {
            follow.recover();
            return new PostFollowResponse(Status.SUCCESS);
        }

        follow.softDelete();
        return new PostFollowResponse(Status.CANCEL);
    }

    public CheckFollowResponse checkFollow(Long followerId, Long followingId) {
        boolean followFlag = followRepository.existByBothId(followerId, followingId);
        return new CheckFollowResponse(followFlag);
    }
}
