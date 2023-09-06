package tripagramex.domain.follow.repository.mock;

import tripagramex.domain.account.entity.Account;
import tripagramex.domain.follow.entity.Follow;
import tripagramex.domain.follow.repository.FollowRepository;

import java.util.Optional;

public class MockFollowRepository implements FollowRepository {

    @Override
    public Follow save(Follow follow) {
        return null;
    }

    @Override
    public Long countFollowing(Account account) {
        return 0L;
    }

    @Override
    public Long countFollower(Account account) {
        return 0L;
    }

    @Override
    public Optional<Follow> findByFollowerAndFollowing(Account Follower, Account Following) {
        return Optional.empty();
    }
}
