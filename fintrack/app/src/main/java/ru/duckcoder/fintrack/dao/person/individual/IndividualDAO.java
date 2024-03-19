package ru.duckcoder.fintrack.dao.person.individual;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.person.PersonDAO;
import ru.duckcoder.fintrack.model.Individual;

@Log4j2
public class IndividualDAO extends PersonDAO<Individual> {
    public IndividualDAO(EntityManager entityManager) {
        super(entityManager, Individual.class);
    }
}
