package ru.duckcoder.fintrack.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.model.AbstractModel;
import ru.duckcoder.fintrack.model.BaseEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Getter(value = AccessLevel.PROTECTED)
@Log4j2
public class AbstractDAO<M extends BaseEntity, ID> implements AutoCloseable, EntityDeleteById<ID> {
    private final Class<M> entityClass;
    @PersistenceContext
    private EntityManager entityManager;

    protected AbstractDAO(EntityManager entityManager, Class<M> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public Optional<M> findById(ID id) {
        M model = this.getEntityManager().find(this.getEntityClass(), id);
        if (model == null)
            return Optional.empty();
        return Optional.of(model);
    }

    public List<M> findAll() {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<M> cq = cb.createQuery(this.getEntityClass());
        Root<M> r = cq.from(this.getEntityClass());
        cq.select(r);
        TypedQuery<M> query = this.getEntityManager().createQuery(cq);
        return query.getResultList();
    }

    public M save(M entity) {
        EntityTransaction transaction = this.getEntityManager().getTransaction();
        try {
            transaction.begin();
            M persistent = this.getEntityManager().merge(entity);
            transaction.commit();
            this.getEntityManager().detach(persistent);
            return persistent;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public void delete(M entity) {
        EntityTransaction transaction = this.getEntityManager().getTransaction();
        try {
            transaction.begin();
            this.getEntityManager().remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ID id) {
        try {
            M persistent = this.findById(id).orElseThrow();
            this.delete(persistent);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        this.entityManager.close();
    }
}


