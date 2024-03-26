package ru.duckcoder.fintrack.backend.dao.person.individual;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.dao.AbstractDAO;
import ru.duckcoder.fintrack.backend.dao.EntityDeleteById;
import ru.duckcoder.fintrack.backend.model.person.individual.Individual;

import java.util.List;
import java.util.Optional;

@Log4j2
public class IndividualDAO extends AbstractDAO<Individual, Long> implements EntityDeleteById<Long> {
    public IndividualDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Individual> findById(Long id) {
        Individual entity = this.getEntityManager().find(Individual.class, id);
        if (entity == null)
            return Optional.empty();
//        this.getEntityManager().detach(entity);
        return Optional.of(entity);
    }

    @Override
    public List<Individual> findAll() {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Individual> cq = cb.createQuery(Individual.class);
        Root<Individual> r = cq.from(Individual.class);
        cq.select(r);
        TypedQuery<Individual> query = this.getEntityManager().createQuery(cq);
        List<Individual> entities = query.getResultList();
        return entities/*.stream()
                .peek(this.getEntityManager()::detach)
                .toList()*/;
    }

    @Override
    public Individual save(Individual entity) {
        return this.getEntityManager().merge(entity);
    }

    @Override
    public void delete(Individual entity) {
        entity.setUser(null);
        this.getEntityManager().remove(entity);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent(this::delete);
    }
}
