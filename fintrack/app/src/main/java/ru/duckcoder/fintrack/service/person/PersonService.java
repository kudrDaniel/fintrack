package ru.duckcoder.fintrack.service.person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.person.PersonDAO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;
import ru.duckcoder.fintrack.dto.person.PersonUpdateDTO;
import ru.duckcoder.fintrack.mapper.AccountMapper;
import ru.duckcoder.fintrack.model.person.Person;
import ru.duckcoder.fintrack.service.AbstractService;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class PersonService extends AbstractService<PersonDTO, PersonCreateDTO, PersonUpdateDTO, Long> {
    private static volatile PersonService instance;

    public static PersonService getInstance() {
        PersonService localInstance = instance;
        if (localInstance == null) {
            synchronized (PersonService.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new PersonService();
            }
        }
        return localInstance;
    }

    @PersistenceContext
    private final EntityManager entityManager = super.getEntityManager();
    private final PersonDAO dao = new PersonDAO(this.entityManager);

    @Override
    protected PersonDTO create(PersonCreateDTO dto) {
        throw new UnsupportedOperationException("Unable to create abstract person");
    }

    @Override
    public List<PersonDTO> readAll() {
        try {
            List<Person> models = this.dao.findAll();
            AccountMapper mapper = new AccountMapper(this.entityManager);
            return models.stream()
                    .map(model -> {
                        AccountDTO accountDTO = null;
                        if (model.getAccount() != null)
                            accountDTO = mapper.map(model.getAccount());
                        return new PersonDTO(model.getId(), accountDTO, model.getLabel());
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PersonDTO read(Long id) {
        try {
            Person model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Person with id:" + id + " not found"));
            AccountDTO accountDTO = new AccountMapper(this.entityManager).map(model.getAccount());
            return new PersonDTO(model.getId(), accountDTO, model.getLabel());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected PersonDTO update(Long id, PersonUpdateDTO dto) {
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

    @Override
    public void close() throws Exception {
        this.entityManager.close();
        super.close();
    }
}
