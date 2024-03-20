package ru.duckcoder.fintrack.mapper;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dto.AbstractCreateDTO;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.dto.AbstractUpdateDTO;
import ru.duckcoder.fintrack.model.AbstractEntity;

@Log4j2
@Getter(value = AccessLevel.PROTECTED)
public abstract class AbstractMapper<
        D extends AbstractDTO,
        CD extends AbstractCreateDTO,
        UD extends AbstractUpdateDTO,
        E extends AbstractEntity> {
    private final EntityManager entityManager;

    protected AbstractMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    abstract E map(CD dto);
    abstract D map(E model);
    abstract void update(UD dto, E model);

    protected <TE extends AbstractEntity, ID> TE toEntity(Class<TE> entityClass, ID id) {
        if (id == null)
            return null;
        return this.entityManager.find(entityClass, id);
    }
}
