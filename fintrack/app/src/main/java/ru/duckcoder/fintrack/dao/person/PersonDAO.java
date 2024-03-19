package ru.duckcoder.fintrack.dao.person;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.AbstractDAO;
import ru.duckcoder.fintrack.model.Person;

@Log4j2
public class PersonDAO<M extends Person> extends AbstractDAO<M, Long> {
    public PersonDAO(EntityManager entityManager, Class<M> entityClass) {
        super(entityManager, entityClass);
    }
}
