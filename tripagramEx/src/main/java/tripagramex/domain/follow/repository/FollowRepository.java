package tripagramex.domain.follow.repository;

import org.springframework.stereotype.Repository;
import tripagramex.domain.account.entity.Account;

@Repository
public interface FollowRepository {

    Long countByFollowing(Account account);

    Long countByFollower(Account account);
}
