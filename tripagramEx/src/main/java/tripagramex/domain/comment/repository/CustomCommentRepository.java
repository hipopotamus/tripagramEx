package tripagramex.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomCommentRepository implements CommentInquiryRepositoryByQueryDsl{

    private final JPAQueryFactory jpaQueryFactory;
}
