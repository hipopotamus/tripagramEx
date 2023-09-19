package tripagramex.domain.likes.repository.mock;

import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.likes.entity.Likes;
import tripagramex.domain.likes.repository.LikeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockLikeRepository implements LikeRepository {

    public static List<Likes> store = new ArrayList<>();
    public static long sequence = 40001L;

    public MockLikeRepository() {
        initiate();
    }

    @Override
    public Likes save(Likes likes) {
        Long id = likes.getId();
        if (id == null || id == 0L) {
            increaseSequence();
            Likes savedLikes = Likes.builder()
                    .account(likes.getAccount())
                    .board(likes.getBoard())
                    .build();
            store.add(savedLikes);
            return savedLikes;
        } else {
            store.removeIf(item -> item.getId().equals(id));
            store.add(likes);
            return likes;
        }
    }

    @Override
    public Optional<Likes> findByAccountAndBoard(Account account, Board board) {
        return store.stream()
                .filter(likes -> (likes.getAccount().getId().equals(account.getId()) &&
                        likes.getBoard().getId().equals(board.getId())))
                .findFirst();
    }

    @Override
    public boolean existByBothId(Long accountId, Long boardId) {
        return false;
    }

    @Override
    public Long countBoardLike(Long boardId) {
        return 1L;
    }

    public void initiate() {
        store.clear();
        sequence = 40001L;
        initSample();
    }

    private void initSample() {
        saveSampleLike(10001L, 30001L);
    }

    private void saveSampleLike(Long accountId, Long boardId) {
        Account account = Account.builder()
                .id(accountId)
                .build();
        Board board = Board.builder()
                .id(boardId)
                .build();
        Likes likes = Likes.builder()
                .id(40001L)
                .account(account)
                .board(board)
                .build();
        save(likes);
    }

    private void increaseSequence() {
        while (true) {
            if (!existLikeById(++sequence)) {
                break;
            }
        }
    }

    private boolean existLikeById(long id) {
        Optional<Likes> OptionalLikes = store.stream()
                .filter(likes -> likes.getId().equals(id))
                .findFirst();
        return OptionalLikes.isPresent();
    }
}
