package tripagramex.domain.comment.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentInquiryService {
}
