package tripagramex.domain.likes.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.likes.dto.PostLikeResponse;
import tripagramex.domain.likes.entity.Likes;
import tripagramex.domain.likes.repository.LikeRepository;
import tripagramex.global.common.enums.Status;

import java.util.Optional;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public PostLikeResponse postLike(Account account, Board board) {
        Optional<Likes> optionalLikes = likeRepository.findByAccountAndBoard(account, board);
        if (optionalLikes.isEmpty()) {
            Likes likes = Likes.builder()
                    .account(account)
                    .board(board)
                    .build();

            likeRepository.save(likes);
            return new PostLikeResponse(Status.SUCCESS);
        }

        Likes likes = optionalLikes.get();
        if (likes.isDeleted()) {
            likes.recover();
            return new PostLikeResponse(Status.SUCCESS);
        }

        likes.softDelete();
        return new PostLikeResponse(Status.CANCEL);
    }
}
