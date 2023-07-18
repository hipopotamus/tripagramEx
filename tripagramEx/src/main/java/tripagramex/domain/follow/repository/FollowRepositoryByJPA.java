package tripagramex.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.follow.entity.Follow;

public interface FollowRepositoryByJPA extends FollowRepository, JpaRepository<Follow, Long> {
}
