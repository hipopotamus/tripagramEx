package tripagramex.domain.board.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tripagramex.domain.board.entity.Board;
import tripagramex.domain.board.enums.Category;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadResponse {

    private Long boardId;

    private Long accountId;

    private String title;

    private String content;

    private String location;

    private String thumbnail;

    private Integer view;

    private Category category;

    private List<String> images;

    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public static ReadResponse of(Board board) {
        return ReadResponse.builder()
                .boardId(board.getId())
                .accountId(board.getAccount().getId())
                .title(board.getTitle())
                .content(board.getContent())
                .location(board.getLocation())
                .thumbnail(board.getThumbnail())
                .view(board.getViews())
                .category(board.getCategory())
                .images(board.getImages())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
