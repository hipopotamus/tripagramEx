package tripagramex.domain.comment.dto;

import lombok.Builder;
import lombok.Data;
import tripagramex.domain.board.entity.Board;

@Data
@Builder
public class BoardDto {

    private Long id;

    private String title;

    private String thumbnail;

    public static BoardDto of(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .thumbnail(board.getThumbnail())
                .build();
    }
}
