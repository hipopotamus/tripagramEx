package tripagramex.domain.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.enums.Category;

import java.util.List;

@Data
public class CreateRequest {

    @NotNull
    @Length(min = 5, max = 40)
    private String title;

    @NotNull
    @Length(min = 5, max = 2000)
    private String content;

    private String location;

    private String thumbnail;

    @NotNull
    private Category category;

    @NotNull
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
