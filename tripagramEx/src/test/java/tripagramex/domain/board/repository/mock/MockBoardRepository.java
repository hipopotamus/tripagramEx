package tripagramex.domain.board.repository.mock;

import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.enums.Category;
import tripagramex.domain.board.repository.BoardRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MockBoardRepository implements BoardRepository {

    public static List<Board> store = new ArrayList<>();
    public static long sequence = 30000L;

    public MockBoardRepository() {
        initiate();
    }

    @Override
    public Board save(Board board) {
        Long id = board.getId();
        if (id == null || id == 0L) {
            increaseSequence();
            Board savedBoard = Board.builder()
                    .id(sequence)
                    .account(board.getAccount())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .location(board.getLocation())
                    .thumbnail(board.getThumbnail())
                    .category(board.getCategory())
                    .images(board.getImages())
                    .build();
            store.add(savedBoard);
            return savedBoard;
        } else {
            store.removeIf(item -> item.getId().equals(id));
            store.add(board);
            return board;
        }
    }

    @Override
    public Optional<Board> findById(Long boardId) {
        return store.stream()
                .filter(board -> board.getId().equals(boardId) && !board.isDeleted())
                .findAny();
    }

    @Override
    public Optional<Board> findWithAccount(Long boardId) {
        return store.stream()
                .filter(board -> board.getId().equals(boardId) && !board.isDeleted())
                .findAny();
    }

    @Override
    public boolean existsById(Long boardId) {
        return false;
    }

    public void initiate() {
        store.clear();
        sequence = 30000L;
        initSample();
    }

    private void initSample() {
        saveSample(1L);
    }

    private void saveSample(Long number) {
        for (long i = 1L; i <= number; i++) {
            Account account = Account.builder()
                    .id(10000 + i)
                    .email("test" + i +"@test.com")
                    .password("[Encode]test" + i + "Password")
                    .nickname("test" + i + "Nickname")
                    .profile("test" + i + "Profile")
                    .intro("test" + i + "Intro")
                    .build();

            Board board = Board.builder()
                    .id(30000 + i)
                    .account(account)
                    .title("test" + i + "Title")
                    .content("test" + i + "Content")
                    .location("test" + i + "Location")
                    .thumbnail("test" + i + "Thumbnail")
                    .category(Category.RESTAURANT)
                    .images(new ArrayList<>(Arrays.asList("test" + i + "Image1", "test" + i + "Image2")))
                    .build();
            save(board);
        }
    }

    private void increaseSequence() {
        while (true) {
            if (!existBoardById(++sequence)) {
                break;
            }
        }
    }

    private boolean existBoardById(long id) {
        Optional<Board> OptionalBoard = store.stream()
                .filter(board -> (board.getId().equals(id) && !board.isDeleted()))
                .findFirst();
        return OptionalBoard.isPresent();
    }
}
