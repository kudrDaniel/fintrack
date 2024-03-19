package ru.duckcoder.fintrack.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.person.PersonDAO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;
import ru.duckcoder.fintrack.dto.person.PersonUpdateDTO;
import ru.duckcoder.fintrack.mapper.AccountMapper;
import ru.duckcoder.fintrack.mapper.PersonMapper;
import ru.duckcoder.fintrack.model.Person;

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

    private final PersonDAO<Person> dao = new PersonDAO<>(this.getEntityManager(), Person.class);

    @Override
    PersonDTO create(PersonCreateDTO dto) {
        throw new UnsupportedOperationException("Unable to create abstract person");
    }

    @Override
    public List<PersonDTO> read() {
        List<Person> models = this.dao.findAll();
        AccountMapper mapper = new AccountMapper();
        return models.stream()
                .map(model -> {
                    AccountDTO accountDTO = mapper.map(model.getAccount());
                    return new PersonDTO(model.getId(), accountDTO, model.getLabel());
                })
                .toList();
    }

    @Override
    public PersonDTO read(Long id) {
        Person model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Person with id:" + id + " not found"));
        AccountDTO accountDTO = new AccountMapper().map(model.getAccount());
        return new PersonDTO(model.getId(), accountDTO, model.getLabel());
    }

    @Override
    PersonDTO update(Long id, PersonUpdateDTO dto) {
        throw new UnsupportedOperationException("Unable to update abstract person");
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
