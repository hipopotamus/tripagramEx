package tripagramex.domain.likes.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.likes.dto.CheckLikeResponse;
import tripagramex.domain.likes.dto.CountBoardLikeResponse;
import tripagramex.domain.likes.dto.PostLikeResponse;
import tripagramex.domain.likes.repository.mock.MockLikeRepository;
import tripagramex.global.common.enums.Status;

import static org.assertj.core.api.Assertions.assertThat;

class LikeServiceTest {

    private final MockLikeRepository likeRepository = new MockLikeRepository();
    private final LikeService likeService = LikeService.builder()
            .likeRepository(likeRepository)
            .build();

    @AfterEach
    void after() {
        likeRepository.initiate();
    }

    @Test
    @DisplayName("좋아요 시행_성공")
    void postLikeTest_Success() {
        //given
        Account account = Account.builder()
                .id(10001L)
                .build();
        Board board = Board.builder()
                .id(30002L)
                .build();

        //when
        PostLikeResponse postLikeResponse = likeService.postLike(account, board);

        //then
        assertThat(postLikeResponse.getStatus()).isEqualTo(Status.SUCCESS);
    }

    @Test
    @DisplayName("좋아요 시행_취소")
    void postLikeTest_Cancel() {
        //given
        Account account = Account.builder()
                .id(10001L)
                .build();
        Board board = Board.builder()
                .id(30001L)
                .build();

        //when
        PostLikeResponse postLikeResponse = likeService.postLike(account, board);

        //then
        assertThat(postLikeResponse.getStatus()).isEqualTo(Status.CANCEL);
    }

    @Test
    @DisplayName("좋아요 반복 시행_성공")
    void repeatPostLikeTest_Success() {
        //given
        Account account = Account.builder()
                .id(10001L)
                .build();
        Board board = Board.builder()
                .id(30002L)
                .build();

        //when
        likeService.postLike(account, board);
        likeService.postLike(account, board);
        likeService.postLike(account, board);
        likeService.postLike(account, board);
        PostLikeResponse postLikeResponse = likeService.postLike(account, board);

        //then
        assertThat(postLikeResponse.getStatus()).isEqualTo(Status.SUCCESS);
    }

    @Test
    @DisplayName("좋아요 여부 조회_성공")
    void checkLikeTest_Success() {
        //given
        Long accountId = 10001L;
        Long boardId = 30002L;

        //when
        CheckLikeResponse checkLikeResponse = likeService.checkLike(accountId, boardId);

        //then
        assertThat(checkLikeResponse.isLikes()).isFalse();
    }

    @Test
    @DisplayName("게시물의 좋아요 개수 조회_성공")
    void countBoardLikeTest_Success() {
        //given
        Long boardId = 30001L;

        //when
        CountBoardLikeResponse countBoardLikeResponse = likeService.countBoardLike(boardId);

        //then
        assertThat(countBoardLikeResponse.getLikeCount()).isEqualTo(1L);
    }
}