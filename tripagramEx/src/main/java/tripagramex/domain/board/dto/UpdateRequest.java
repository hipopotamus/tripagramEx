package tripagramex.domain.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.enums.Category;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {

    @Length(min = 5, max = 40)
    private String title;

    @Length(min = 5, max = 2000)
    private String content;

    private String location;

    @NotNull
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
