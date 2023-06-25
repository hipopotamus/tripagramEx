package tripagramex.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.follow.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Long countByFollowing(Account account);

    Long countByFollower(Account account);
}
