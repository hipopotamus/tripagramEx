package tripagramex.domain.board.dto;

import lombok.Data;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.enums.Category;

import java.util.List;

@Data
public class UpdateRequest {

    private String title;

    private String content;

    private String location;

    private String thumbnail;

    private Category category;

    private List<String> images;

    public Board toBoard() {
        return Board.builder()
                .title(title)
                .content(content)
                .location(location)
                .thumbnail(thumbnail)
                .category(category)
                .images(images)
                .build();
    }
}
