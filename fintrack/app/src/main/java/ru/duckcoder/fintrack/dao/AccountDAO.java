package ru.duckcoder.fintrack.dao;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.duckcoder.fintrack.dao.extension.EntityDeleteById;
import ru.duckcoder.fintrack.model.Account;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
public class AccountDAO extends EntityDAO<Account, Long> implements
        EntityDeleteById<Long> {
    public AccountDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Account> findById(Long id) {
        try (Session session = this.getSessionFactory().openSession()) {
            Account account = session.get(Account.class, id);
            return Optional.of(account);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        try (Session session = this.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Account", Account.class).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public Account save(Account entity) {
        Transaction transaction = null;
        try (Session session = this.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Account persistent = session.merge(entity);
            transaction.commit();
            session.evict(persistent);
            entity.setId(persistent.getId());
            entity.setUsername(persistent.getUsername());
            entity.setPassword(persistent.getPassword());
            entity.setPersons(persistent.getPersons());
            return persistent;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void delete(Account entity) {
        Transaction transaction = null;
        try (Session session = this.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            entity.setId(0L);
            entity.setUsername(null);
            entity.setPassword(null);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Account persistent = this.findById(id).orElseThrow();
            this.delete(persistent);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage(), e);
        }
    }
}
