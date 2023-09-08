package tripagramex.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tripagramex.global.common.enums.Status;

@Data
@AllArgsConstructor
public class PostFollowResponse {

    private Status status;
}
