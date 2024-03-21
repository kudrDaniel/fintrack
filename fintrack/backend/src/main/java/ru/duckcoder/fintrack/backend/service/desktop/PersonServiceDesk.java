package ru.duckcoder.fintrack.backend.service.desktop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.config.EntityManagerProvider;
import ru.duckcoder.fintrack.backend.dao.person.PersonDAO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonUpdateDTO;
import ru.duckcoder.fintrack.backend.mapper.AccountMapper;
import ru.duckcoder.fintrack.backend.model.person.Person;
import ru.duckcoder.fintrack.backend.service.PersonService;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class PersonServiceDesk implements PersonService {
    @PersistenceContext
    private EntityManager entityManager;
    private final PersonDAO dao;

    public PersonServiceDesk() {
        this.entityManager = EntityManagerProvider.getInstance().getEntityManager();
        this.dao = new PersonDAO(this.entityManager);
    }

    @Override
    public PersonDTO create(PersonCreateDTO dto) {
        throw new UnsupportedOperationException("Unable to create abstract person");
    }

    @Override
    public List<PersonDTO> readAll() {
        List<PersonDTO> result;
        try {
            List<Person> models = this.dao.findAll();
            AccountMapper mapper = new AccountMapper(this.entityManager);
            result = models.stream()
                    .map(model -> {
                        AccountDTO accountDTO = null;
                        if (model.getAccount() != null) {
                            accountDTO = mapper.map(model.getAccount());
                        }
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
    public PersonDTO read(Long id) {
        PersonDTO result;
        try {
            Person model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Person with id:" + id + " not found"));
            AccountDTO accountDTO = new AccountMapper(this.entityManager).map(model.getAccount());
            this.entityManager.detach(model);
            result = new PersonDTO(model.getId(), accountDTO, model.getLabel());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public PersonDTO update(Long id, PersonUpdateDTO dto) {
        throw new UnsupportedOperationException("Unable to update abstract person");
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
