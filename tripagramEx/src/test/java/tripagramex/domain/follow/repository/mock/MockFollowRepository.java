package tripagramex.domain.follow.repository.mock;

import tripagramex.domain.account.entity.Account;
import tripagramex.domain.follow.repository.FollowRepository;

public class MockFollowRepository implements FollowRepository {

    @Override
    public Long countByFollowing(Account account) {
        return 0L;
    }

    @Override
    public Long countByFollower(Account account) {
        return 0L;
    }
}
