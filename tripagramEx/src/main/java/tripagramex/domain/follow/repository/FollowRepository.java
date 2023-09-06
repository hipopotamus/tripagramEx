package tripagramex.domain.follow.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.follow.entity.Follow;

import java.util.Optional;

@Repository
public interface FollowRepository {

    Follow save(Follow follow);

    @Query("select count(follow) from Follow follow " +
            "where follow.follower = :account and follow.deleted = false")
    Long countFollowing(@Param("account") Account account);

    @Query("select count(follow) from Follow follow " +
            "where follow.following = :account and follow.deleted = false")
    Long countFollower(@Param("account") Account account);

    Optional<Follow> findByFollowerAndFollowing(Account Follower, Account Following);
}
