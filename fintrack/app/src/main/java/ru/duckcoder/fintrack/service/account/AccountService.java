package ru.duckcoder.fintrack.service.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.account.AccountDAO;
import ru.duckcoder.fintrack.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;
import ru.duckcoder.fintrack.mapper.AccountMapper;
import ru.duckcoder.fintrack.model.account.Account;
import ru.duckcoder.fintrack.model.person.Person;
import ru.duckcoder.fintrack.service.AbstractService;

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

    @PersistenceContext
    private final EntityManager entityManager = super.getEntityManager();
    private final AccountDAO dao = new AccountDAO(this.entityManager);
    private final AccountMapper mapper = new AccountMapper(this.entityManager);

    public List<PersonDTO> readAllPersons(Long id) {
        try {
            Account accountModel = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Account with id:" + id + " not found"));
            log.debug("Found account with id:" + id);
            List<Person> personModels = this.dao.findAllPersons(id);
            AccountDTO accountDTO = mapper.map(accountModel);
            return personModels.stream()
                    .map(model -> new PersonDTO(model.getId(), accountDTO, model.getLabel()))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDTO create(AccountCreateDTO dto) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Account model = this.mapper.map(dto);
            model = this.dao.save(model);
            transaction.commit();
            return this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AccountDTO> readAll() {
        try {
            List<Account> models = this.dao.findAll();
            return models.stream()
                    .map(this.mapper::map)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDTO read(Long id) {
        try {
            Account model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Account with id:" + id + " not found"));
            log.debug("Found account with id:" + id);
            return this.mapper.map(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDTO update(Long id, AccountUpdateDTO dto) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Account model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Account with id:" + id + " not found"));
            this.mapper.update(dto, model);
            model = this.dao.save(model);
            transaction.commit();
            return this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.dao.deleteById(id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        this.entityManager.close();
        super.close();
    }
}
