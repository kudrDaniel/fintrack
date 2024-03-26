package ru.duckcoder.fintrack.backend.dao.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.dao.AbstractDAO;
import ru.duckcoder.fintrack.backend.dao.EntityDeleteById;
import ru.duckcoder.fintrack.backend.model.user.User;
import ru.duckcoder.fintrack.backend.model.person.Person;

import java.util.List;
import java.util.Optional;

@Log4j2
public class UserDAO extends AbstractDAO<User, Long> implements EntityDeleteById<Long> {
    public UserDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Person> findAllPersons(Long id) {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> r = cq.from(Person.class);
        cq.select(r).where(cb.equal(r.get("user").get("id"), id));
        TypedQuery<Person> query = this.getEntityManager().createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        User entity = this.getEntityManager().find(User.class, id);
        if (entity == null) {
            return Optional.empty();
        }
        entity.setPersons(this.findAllPersons(id));
        return Optional.of(entity);
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> r = cq.from(User.class);
        cq.select(r);
        TypedQuery<User> query = this.getEntityManager().createQuery(cq);
        List<User> userModels = query.getResultList();
        return userModels.stream()
                .peek(accountModel -> {
                    List<Person> personModels = this.findAllPersons(accountModel.getId());
                    accountModel.getPersons().addAll(personModels);
                })
                .toList();
    }

    @Override
    public User save(User entity) {
        return this.getEntityManager().merge(entity);
    }

    @Override
    public void delete(User entity) {
        this.findAllPersons(entity.getId()).stream()
                .peek(person -> person.setUser(null))
                .forEach(person -> this.getEntityManager().merge(person));
        this.getEntityManager().remove(entity);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent(this::delete);
    }
}
