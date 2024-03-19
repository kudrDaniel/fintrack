package ru.duckcoder.fintrack.dao.person.company;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.person.PersonDAO;
import ru.duckcoder.fintrack.model.Company;

@Log4j2
public class CompanyDAO extends PersonDAO<Company> {
    public CompanyDAO(EntityManager entityManager) {
        super(entityManager, Company.class);
    }
}
