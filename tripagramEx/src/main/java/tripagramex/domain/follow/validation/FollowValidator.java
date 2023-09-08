package tripagramex.domain.follow.validation;

public interface FollowValidator {

    void verifySelfFollow(Long followerId, Long followingId);
}
