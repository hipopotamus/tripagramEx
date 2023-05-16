package tripagramex.domain.account.repository;

import tripagramex.domain.account.entity.Account;

import java.util.List;

public interface AccountRepositoryCustom {

    List<Account> findTestAccountList();
}
