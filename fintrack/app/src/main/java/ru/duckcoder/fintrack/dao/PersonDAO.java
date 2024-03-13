package ru.duckcoder.fintrack.dao;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.duckcoder.fintrack.dao.extension.EntityDeleteById;
import ru.duckcoder.fintrack.model.Person;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
public class PersonDAO extends EntityDAO<Person, Long> implements
        EntityDeleteById<Long> {
    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Person> findById(Long id) {
        try (Session session = this.getSessionFactory().openSession()) {
            Person person = session.get(Person.class, id);
            return Optional.of(person);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Person> findAll() {
        try (Session session = this.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Person", Person.class).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public Person save(Person entity) {
        Transaction transaction = null;
        try (Session session = this.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Person persistent = session.merge(entity);
            transaction.commit();
            entity.setId(persistent.getId());
            entity.setAccount(persistent.getAccount());
            entity.setPersonType(persistent.getPersonType());
            entity.setIndividual(persistent.getIndividual());
            entity.setCompany(persistent.getCompany());
            return persistent;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void delete(Person entity) {
        Transaction transaction = null;
        try (Session session = this.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            entity.setId(0L);
            entity.setAccount(null);
            entity.setPersonType(null);
            entity.setIndividual(null);
            entity.setCompany(null);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Person persistent = this.findById(id).orElseThrow();
            this.delete(persistent);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage(), e);
        }
    }
}
