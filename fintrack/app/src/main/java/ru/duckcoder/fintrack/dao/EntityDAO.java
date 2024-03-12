package ru.duckcoder.fintrack.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import ru.duckcoder.fintrack.model.BaseEntity;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public abstract class EntityDAO<E extends BaseEntity, ID> {
    private final SessionFactory sessionFactory;

    abstract Optional<E> findById(ID id);
    abstract List<E> findAll();
    abstract E save(E entity);
    abstract void delete(E entity);
}


