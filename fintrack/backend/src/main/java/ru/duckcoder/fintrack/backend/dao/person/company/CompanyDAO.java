package ru.duckcoder.fintrack.backend.dao.person.company;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.dao.AbstractDAO;
import ru.duckcoder.fintrack.backend.dao.EntityDeleteById;
import ru.duckcoder.fintrack.backend.model.person.company.Company;

import java.util.List;
import java.util.Optional;

@Log4j2
public class CompanyDAO extends AbstractDAO<Company, Long> implements EntityDeleteById<Long> {
    public CompanyDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Company> findById(Long id) {
        Company entity = this.getEntityManager().find(Company.class, id);
        if (entity == null)
            return Optional.empty();
        return Optional.of(entity);
    }

    @Override
    public List<Company> findAll() {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Company> cq = cb.createQuery(Company.class);
        Root<Company> r = cq.from(Company.class);
        cq.select(r);
        TypedQuery<Company> query = this.getEntityManager().createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Company save(Company entity) {
        return this.getEntityManager().merge(entity);
    }

    @Override
    public void delete(Company entity) {
        entity.setAccount(null);
        this.getEntityManager().remove(entity);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent(this::delete);
    }
}
