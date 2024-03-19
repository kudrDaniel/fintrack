package ru.duckcoder.fintrack.service;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.account.AccountDAO;
import ru.duckcoder.fintrack.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.mapper.AccountMapper;
import ru.duckcoder.fintrack.model.Account;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class AccountService extends AbstractService<AccountDTO, AccountCreateDTO, AccountUpdateDTO, Long> {
    private static volatile AccountService instance;

    public static AccountService getInstance() {
        AccountService localInstance = instance;
        if (localInstance == null) {
            synchronized (AccountService.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new AccountService();
            }
        }
        return localInstance;
    }

    private final AccountDAO dao = new AccountDAO(this.getEntityManager());
    private final AccountMapper mapper = new AccountMapper();

    @Override
    public AccountDTO create(AccountCreateDTO dto) {
        Account model = this.mapper.map(dto);
        model = this.dao.save(model);
        return this.mapper.map(model);
    }

    @Override
    public List<AccountDTO> read() {
        List<Account> models = this.dao.findAll();
        return models.stream()
                .map(this.mapper::map)
                .toList();
    }

    @Override
    public AccountDTO read(Long id) {
        Account model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Account with id:" + id + " not found"));
        log.debug("Found account with id:" + id);
        return this.mapper.map(model);
    }

    @Override
    public AccountDTO update(Long id, AccountUpdateDTO dto) {
        Account model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Account with id:" + id + " not found"));
        this.mapper.update(dto, model);
        model = this.dao.save(model);
        return this.mapper.map(model);
    }

    @Override
    public void delete(Long id) {
        this.dao.deleteById(id);
    }

    @Override
    public void close() throws Exception {
        this.dao.close();
        super.close();
    }
}
