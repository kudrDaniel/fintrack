package ru.duckcoder.fintrack.dao.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.EntityType;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.AbstractDAO;
import ru.duckcoder.fintrack.dao.EntityDeleteById;
import ru.duckcoder.fintrack.model.account.Account;
import ru.duckcoder.fintrack.model.person.Person;

import java.util.List;
import java.util.Optional;

@Log4j2
public class AccountDAO extends AbstractDAO<Account, Long> implements EntityDeleteById<Long> {
    public AccountDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Person> findAllPersons(Long id) {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> r = cq.from(Person.class);
        cq.select(r).where(cb.equal(r.get("account").get("id"), id));
        TypedQuery<Person> query = this.getEntityManager().createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Optional<Account> findById(Long id) {
        Account entity = this.getEntityManager().find(Account.class, id);
        if (entity == null) {
            return Optional.empty();
        }
        entity.setPersons(this.findAllPersons(id));
        return Optional.of(entity);
    }

    @Override
    public List<Account> findAll() {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> r = cq.from(Account.class);
        cq.select(r);
        TypedQuery<Account> query = this.getEntityManager().createQuery(cq);
        List<Account> accountModels = query.getResultList();
        return accountModels.stream()
                .peek(accountModel -> {
                    List<Person> personModels = this.findAllPersons(accountModel.getId());
                    accountModel.getPersons().addAll(personModels);
                })
                .toList();
    }

    @Override
    public Account save(Account entity) {
        return this.getEntityManager().merge(entity);
    }

    @Override
    public void delete(Account entity) {
        this.findAllPersons(entity.getId()).stream()
                .peek(person -> person.setAccount(null))
                .forEach(person -> this.getEntityManager().merge(person));
        this.getEntityManager().remove(entity);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent(this::delete);
    }
}
