package codes.showme.repository.impl;

import codes.showme.domain.Account;
import codes.showme.domain.AccountRepository;
import codes.showme.techlib.ioc.InstanceFactory;
import io.ebean.Database;

public class AccountRepositoryImpl implements AccountRepository {
    @Override
    public void save(Account account) {
        Database instance = InstanceFactory.getInstance(Database.class);
        instance.save(account);
    }
}
