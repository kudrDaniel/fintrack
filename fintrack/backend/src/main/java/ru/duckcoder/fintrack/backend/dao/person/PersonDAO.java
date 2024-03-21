package ru.duckcoder.fintrack.backend.dao.person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.dao.AbstractDAO;
import ru.duckcoder.fintrack.backend.dao.EntityDeleteById;
import ru.duckcoder.fintrack.backend.model.person.Person;

import java.util.List;
import java.util.Optional;

@Log4j2
public class PersonDAO extends AbstractDAO<Person, Long> implements EntityDeleteById<Long> {
    public PersonDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Person> findById(Long id) {
        Person entity = this.getEntityManager().find(Person.class, id);
        if (entity == null)
            return Optional.empty();
//        this.getEntityManager().detach(entity);
        return Optional.of(entity);
    }

    @Override
    public List<Person> findAll() {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> r = cq.from(Person.class);
        cq.select(r);
        TypedQuery<Person> query = this.getEntityManager().createQuery(cq);
        List<Person> entities = query.getResultList();
        return entities/*.stream()
                .peek(this.getEntityManager()::detach)
                .toList()*/;
    }

    @Override
    protected Person save(Person entity) {
        throw new UnsupportedOperationException("Unable to create or update abstract person");
    }

    @Override
    public void delete(Person entity) {
        entity.setAccount(null);
        this.getEntityManager().remove(entity);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent(this::delete);
    }
}
