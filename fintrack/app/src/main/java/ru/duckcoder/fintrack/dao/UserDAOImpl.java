package ru.duckcoder.fintrack.dao;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.duckcoder.fintrack.model.User;

import java.util.List;
import java.util.Optional;

@Log4j2
public class UserDAOImpl extends EntityDAO<User, Long> {
    public UserDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = super.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return Optional.of(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        try (Session session = super.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public User save(User entity) {
        Transaction transaction = null;
        try (Session session = super.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User persistent = session.merge(entity);
            transaction.commit();
            session.evict(persistent);
            entity.setId(persistent.getId());
            entity.setUsername(persistent.getUsername());
            entity.setPassword(persistent.getPassword());
            return entity;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
        return null;
    }

    @Override
    public void delete(User entity) {
        Transaction transaction = null;
        try (Session session = super.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            entity.setId(0L);
            entity.setUsername(null);
            entity.setPassword(null);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }
}
