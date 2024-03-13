package ru.duckcoder.fintrack;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.AccountDAO;
import ru.duckcoder.fintrack.dao.PersonDAO;
import ru.duckcoder.fintrack.dao.extension.EntityDeleteById;
import ru.duckcoder.fintrack.model.Account;
import ru.duckcoder.fintrack.model.Company;
import ru.duckcoder.fintrack.model.Individual;
import ru.duckcoder.fintrack.model.Person;

import java.util.List;

@Log4j2
public class Application {
    public static void main(String[] args) {
        log.info("Entry point reached.");
        AccountDAO accountDAO = new AccountDAO(SessionHandler.getSessionFactory());
        PersonDAO personDAO = new PersonDAO(SessionHandler.getSessionFactory());

        Individual individual0 = Individual.builder()
                .fullName("John Johnson")
                .build();
        Individual individual1 = Individual.builder()
                .fullName("Sam Clinton")
                .build();
        Person person0 = Person.builder()
                .personType(true)
                .individual(individual0)
                .build();
        Person person1 = Person.builder()
                .personType(true)
                .individual(individual1)
                .build();
        Account account0 = Account.builder()
                .username("user")
                .password("qwerty")
                .persons(person0)
                .build();

        Company company0 = Company.builder()
                .shortName("Shop")
                .build();
        Company company1 = Company.builder()
                .shortName("Bank")
                .build();
        Company company2 = Company.builder()
                .shortName("School")
                .build();

        List<Person> personList0 = List.of(
                Person.builder()
                        .personType(false)
                        .company(company0)
                        .build(),
                Person.builder()
                        .personType(false)
                        .company(company1)
                        .build(),
                Person.builder()
                        .personType(false)
                        .company(company2)
                        .build()
        );

        accountDAO.save(account0);
        for (Person person : personList0)
            personDAO.save(person);

        accountDAO.findAll().forEach(log::debug);
        personDAO.findAll().forEach(log::debug);

        personDAO.delete(personDAO.findById(account0.getPersons().get(0).getId()).orElseThrow());

        Account tempAccount = accountDAO.findById(account0.getId()).orElseThrow();
        person1.setAccount(tempAccount);
        personDAO.save(person1);

        accountDAO.findAll().forEach(log::debug);
        personDAO.findAll().forEach(log::debug);

        personList0 = personDAO.findAll();
        for (Person person :personList0)
            ((EntityDeleteById<Long>) personDAO).deleteById(person.getId());

        accountDAO.findAll().forEach(log::debug);
        personDAO.findAll().forEach(log::debug);
    }
}
