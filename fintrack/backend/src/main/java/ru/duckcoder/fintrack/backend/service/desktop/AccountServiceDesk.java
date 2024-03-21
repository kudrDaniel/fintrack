package ru.duckcoder.fintrack.backend.service.desktop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.dao.account.AccountDAO;
import ru.duckcoder.fintrack.core.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.backend.mapper.AccountMapper;
import ru.duckcoder.fintrack.backend.model.account.Account;
import ru.duckcoder.fintrack.backend.model.person.Person;
import ru.duckcoder.fintrack.backend.service.AccountService;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class AccountServiceDesk implements AccountService {
    @PersistenceContext
    private EntityManager entityManager;
    private final AccountDAO dao;
    private final AccountMapper mapper;

    public AccountServiceDesk(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.dao = new AccountDAO(this.entityManager);
        this.mapper = new AccountMapper(this.entityManager);
    }

    public List<PersonDTO> readAllPersons(Long id) {
        List<PersonDTO> result;
        try {
            Account accountModel = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Account with id:" + id + " not found"));
            log.debug("Found account with id:" + id);
            List<Person> personModels = this.dao.findAllPersons(id);
            AccountDTO accountDTO = mapper.map(accountModel);
            result = personModels.stream()
                    .map(model -> {
                        this.entityManager.detach(model);
                        return new PersonDTO(model.getId(), accountDTO, model.getLabel());
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public AccountDTO create(AccountCreateDTO dto) {
        AccountDTO result;
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Account model = this.mapper.map(dto);
            model = this.dao.save(model);
            transaction.commit();
            result = this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<AccountDTO> readAll() {
        List<AccountDTO> result;
        try {
            List<Account> models = this.dao.findAll();
            result = models.stream()
                    .map(this.mapper::map)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public AccountDTO read(Long id) {
        AccountDTO result;
        try {
            Account model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Account with id:" + id + " not found"));
            log.debug("Found account with id:" + id);
            result = this.mapper.map(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public AccountDTO update(Long id, AccountUpdateDTO dto) {
        AccountDTO result;
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Account model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Account with id:" + id + " not found"));
            this.mapper.update(dto, model);
            model = this.dao.save(model);
            transaction.commit();
            result = this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
        return result;
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
}
