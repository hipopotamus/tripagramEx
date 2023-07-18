package tripagramex.domain.account.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRepositoryImplCustom implements AccountRepositoryByQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;
}
