package tripagramex.domain.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripagramex.domain.likes.entity.Likes;

public interface LikeRepositoryByJPA extends LikeRepository, JpaRepository<Likes, Long> {
}
