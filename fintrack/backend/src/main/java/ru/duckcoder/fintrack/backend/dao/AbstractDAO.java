package ru.duckcoder.fintrack.backend.dao;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

@Getter(value = AccessLevel.PROTECTED)
@Log4j2
public abstract class AbstractDAO<E extends AbstractEntity, ID> {
    private final EntityManager entityManager;

    protected AbstractDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected abstract Optional<E> findById(ID id);

    protected abstract List<E> findAll();

    protected abstract E save(E entity);

    protected abstract void delete(E entity);
}


