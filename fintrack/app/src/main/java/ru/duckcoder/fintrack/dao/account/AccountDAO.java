package ru.duckcoder.fintrack.dao.account;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.AbstractDAO;
import ru.duckcoder.fintrack.model.Account;

@Log4j2
public class AccountDAO extends AbstractDAO<Account, Long> {
    public AccountDAO(EntityManager entityManager) {
        super(entityManager, Account.class);
    }
}
