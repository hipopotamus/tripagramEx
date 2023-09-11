package tripagramex.domain.board.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tripagramex.domain.board.dto.CreateRequest;
import tripagramex.domain.board.dto.ReadResponse;
import tripagramex.domain.board.dto.UpdateRequest;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.enums.Category;
import tripagramex.domain.board.repository.mock.MockBoardRepository;
import tripagramex.global.common.dto.IdDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BoardServiceTest {

    private final MockBoardRepository boardRepository = new MockBoardRepository();
    private final BoardCRUDService boardCRUDService = BoardCRUDService.builder()
            .boardRepository(boardRepository)
            .build();

    @AfterEach
    void after() {
        boardRepository.initiate();
    }

    @Test
    @DisplayName("게시물 생성_성공")
    public void createTest_Success() {
        //given
        Long loginAccountId = 10000L;
        String title = "testTitle";
        String content = "testContent";
        String location = "testLocation";
        String thumbnail = "testThumbnail";
        Category category = Category.RESTAURANT;
        List<String> images = new ArrayList<>(List.of("testImage1", "testImage2"));

        CreateRequest createRequest = CreateRequest.builder()
                .title(title)
                .content(content)
                .location(location)
                .thumbnail(thumbnail)
                .category(category)
                .images(images)
                .build();

        //when
        IdDto idDto = boardCRUDService.create(loginAccountId, createRequest);

        //then
        Board board = boardRepository.findById(idDto.getId()).get();
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getLocation()).isEqualTo(location);
        assertThat(board.getThumbnail()).isEqualTo(thumbnail);
        assertThat(board.getCategory()).isEqualTo(category);
        assertThat(board.getImages().get(0)).isEqualTo(images.get(0));
        assertThat(board.getImages().get(1)).isEqualTo(images.get(1));
    }

    @Test
    @DisplayName("게시물 조회_성공")
    public void readTest_Success() {
        //given
        Long boardId = 20001L;

        //when
        ReadResponse readResponse = boardCRUDService.read(boardId);

        //then
        assertThat(readResponse.getAccountId()).isEqualTo(10001L);
        assertThat(readResponse.getTitle()).isEqualTo("test1Title");
        assertThat(readResponse.getContent()).isEqualTo("test1Content");
        assertThat(readResponse.getLocation()).isEqualTo("test1Location");
        assertThat(readResponse.getThumbnail()).isEqualTo("test1Thumbnail");
        assertThat(readResponse.getView()).isEqualTo(0);
        assertThat(readResponse.getCategory()).isEqualTo(Category.RESTAURANT);
        assertThat(readResponse.getImages().get(0)).isEqualTo("test1Image1");
        assertThat(readResponse.getImages().get(1)).isEqualTo("test1Image2");
    }

    @Test
    @DisplayName("게시물 수정_성공")
    public void updateTest_Success() {
        //given
        Long boardId = 20001L;
        String updateTitle = "testUpdateTitle";
        String updateContent = "testUpdateContent";
        String updateLocation = "testUpdateLocation";
        String updateThumbnail = "testUpdateThumbnail";
        Category updateCategory = Category.SPOT;
        List<String> updateImages = new ArrayList<>(List.of("testUpdateImage1", "testUpdateImage2"));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .title(updateTitle)
                .content(updateContent)
                .location(updateLocation)
                .thumbnail(updateThumbnail)
                .category(updateCategory)
                .images(updateImages)
                .build();

        //then
        boardCRUDService.update(boardId, updateRequest);

        //when
        Board board = boardRepository.findById(boardId).get();
        assertThat(board.getTitle()).isEqualTo(updateTitle);
        assertThat(board.getContent()).isEqualTo(updateContent);
        assertThat(board.getLocation()).isEqualTo(updateLocation);
        assertThat(board.getThumbnail()).isEqualTo(updateThumbnail);
        assertThat(board.getCategory()).isEqualTo(updateCategory);
        assertThat(board.getImages().get(0)).isEqualTo(updateImages.get(0));
        assertThat(board.getImages().get(1)).isEqualTo(updateImages.get(1));
    }

    @Test
    @DisplayName("게시물 수정_일부 필드 누락")
    public void updateTest_FieldOmission() {
        //given
        Long boardId = 20001L;
        String updateContent = "testUpdateContent";
        String updateThumbnail = "testUpdateThumbnail";
        List<String> updateImages = new ArrayList<>(List.of("test1Image1", "testUpdateImage2", "testUpdateImage3"));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .content(updateContent)
                .thumbnail(updateThumbnail)
                .images(updateImages)
                .build();

        //then
        boardCRUDService.update(boardId, updateRequest);

        //when
        Board board = boardRepository.findById(boardId).get();
        assertThat(board.getTitle()).isEqualTo("test1Title");
        assertThat(board.getContent()).isEqualTo(updateContent);
        assertThat(board.getLocation()).isEqualTo("test1Location");
        assertThat(board.getThumbnail()).isEqualTo(updateThumbnail);
        assertThat(board.getCategory()).isEqualTo(Category.RESTAURANT);
        assertThat(board.getImages().get(0)).isEqualTo(updateImages.get(0));
        assertThat(board.getImages().get(1)).isEqualTo(updateImages.get(1));
        assertThat(board.getImages().get(2)).isEqualTo(updateImages.get(2));
    }

    @Test
    @DisplayName("게시물 수정_모든 필드 누락")
    public void updateTest_AllFieldOmission() {
        //given
        Long boardId = 20001L;

        UpdateRequest updateRequest = UpdateRequest.builder()
                .build();

        //then
        boardCRUDService.update(boardId, updateRequest);

        //when
        Board board = boardRepository.findById(boardId).get();
        assertThat(board.getTitle()).isEqualTo("test1Title");
        assertThat(board.getContent()).isEqualTo("test1Content");
        assertThat(board.getLocation()).isEqualTo("test1Location");
        assertThat(board.getThumbnail()).isEqualTo("test1Thumbnail");
        assertThat(board.getCategory()).isEqualTo(Category.RESTAURANT);
        assertThat(board.getImages().get(0)).isEqualTo("test1Image1");
        assertThat(board.getImages().get(1)).isEqualTo("test1Image2");
    }


    @Test
    @DisplayName("게시물 삭제_성공")
    public void deleteTest_Success() {
        //given
        Long boardId = 20001L;

        //then
        boardCRUDService.delete(boardId);

        //when
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        assertThat(optionalBoard.isEmpty()).isTrue();
    }
}