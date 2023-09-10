package tripagramex.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryImplCustom implements BoardRepositoryByQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;
}
