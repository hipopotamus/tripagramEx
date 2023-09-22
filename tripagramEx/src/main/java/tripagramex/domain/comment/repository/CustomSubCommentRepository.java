package tripagramex.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomSubCommentRepository implements SubCommentInquiryRepositoryByQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;
}
