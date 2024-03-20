package ru.duckcoder.fintrack.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.model.account.Account;

@Log4j2
public class AccountMapper extends AbstractMapper<
        AccountDTO,
        AccountCreateDTO,
        AccountUpdateDTO,
        Account> {
    public AccountMapper(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Account map(AccountCreateDTO dto) {
        Account model = new Account();
        model.setUsername(dto.getUsername());
        model.setPassword(dto.getPassword());
        return model;
    }

    @Override
    public AccountDTO map(Account model) {
        return new AccountDTO(model.getId(), model.getUsername());
    }

    @Override
    public void update(AccountUpdateDTO dto, Account model) {
        if (dto.getUsername() != null) {
            String newUsername;
            if (dto.getUsername().isPresent())
                newUsername = dto.getUsername().get();
            else {
                log.warn("Username can not be null");
                newUsername = model.getUsername();
            }
            model.setUsername(newUsername);
        } else
            log.debug("Username unchanged");
        if (dto.getPassword() != null) {
            String newPassword;
            if (dto.getPassword().isPresent())
                newPassword = dto.getPassword().get();
            else {
                log.warn("Password can not be null.");
                newPassword = model.getPassword();
            }
            model.setPassword(newPassword);
        } else
            log.debug("Password unchanged");
    }
}
