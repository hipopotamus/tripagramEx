package tripagramex.domain.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tripagramex.global.common.enums.Status;

@Data
@AllArgsConstructor
public class PostLikeResponse {

    private Status status;
}
