package tripagramex.domain.follow.validation;

import org.springframework.stereotype.Component;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

@Component
public class FollowGenericValidator implements FollowValidator {

    @Override
    public void verifySelfFollow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new BusinessLogicException(ExceptionCode.SELF_FOLLOW);
        }
    }
}
