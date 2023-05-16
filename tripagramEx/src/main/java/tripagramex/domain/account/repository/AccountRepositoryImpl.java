package tripagramex.domain.account.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import tripagramex.domain.account.entity.Account;

import java.util.List;

import tripagramex.domain.account.entity.QAccount;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Account> findTestAccountList() {

        return jpaQueryFactory
                .selectFrom(QAccount.account)
                .fetch();
    }
}
