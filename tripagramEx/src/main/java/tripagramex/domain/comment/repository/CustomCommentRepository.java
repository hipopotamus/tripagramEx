package tripagramex.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
