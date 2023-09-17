package tripagramex.domain.follow.repository.mock;

import tripagramex.domain.account.entity.Account;
import tripagramex.domain.follow.entity.Follow;
import tripagramex.domain.follow.repository.FollowRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockFollowRepository implements FollowRepository {

    public static List<Follow> store = new ArrayList<>();
    public static long sequence = 20001L;

    public MockFollowRepository() {
        initiate();
    }

    @Override
    public Follow save(Follow follow) {
        Long id = follow.getId();
        if (id == null || id == 0L) {
            increaseSequence();
            Follow savedFollow = Follow.builder()
                    .id(sequence)
                    .follower(follow.getFollower())
                    .following(follow.getFollowing())
                    .build();
            store.add(savedFollow);
            return savedFollow;
        } else {
            store.removeIf(item -> item.getId().equals(id));
            store.add(follow);
            return follow;
        }
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
        return store.stream()
                .filter(follow -> (follow.getFollower().getId().equals(Follower.getId()) &&
                        follow.getFollowing().getId().equals(Following.getId()) &&
                        !follow.isDeleted()))
                .findFirst();
    }

    @Override
    public boolean existByBothId(Long followerId, Long followingId) {
        return false;
    }

    public void initiate() {
        store.clear();
        sequence = 20001L;
        initSample();
    }

    private void initSample() {
        saveSampleFollow(10001L, 10003L);
    }

    private void saveSampleFollow(Long followerId, Long followingId) {
        Account follower = Account.builder()
                .id(followerId)
                .build();
        Account following = Account.builder()
                .id(followingId)
                .build();
        Follow follow = Follow.builder()
                .id(20001L)
                .follower(follower)
                .following(following)
                .build();
        save(follow);
    }

    private void increaseSequence() {
        while (true) {
            if (!existFollowById(++sequence)) {
                break;
            }
        }
    }

    private boolean existFollowById(long id) {
        Optional<Follow> OptionalFollow = store.stream()
                .filter(follow -> follow.getId().equals(id))
                .findFirst();
        return OptionalFollow.isPresent();
    }
}
