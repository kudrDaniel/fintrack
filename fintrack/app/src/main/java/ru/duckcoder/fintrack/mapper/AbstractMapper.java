package ru.duckcoder.fintrack.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.AbstractDAO;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.model.AbstractModel;
import ru.duckcoder.fintrack.model.BaseEntity;
import ru.duckcoder.fintrack.service.AbstractService;

@Log4j2
public abstract class AbstractMapper<
        D extends AbstractDTO,
        DC extends AbstractDTO,
        DU extends AbstractDTO,
        M extends AbstractModel> {
    abstract M map(DC dto);
    abstract D map(M model);
    abstract void update(DU dto, M model);

    protected <E extends BaseEntity> E toEntity(Long id, Class<E> entityClass, AbstractService service) {
        E entity;
        try (EntityManager entityManager = service.getEntityManager()) {
            if (id != null) {
                entity = entityManager.find(entityClass, id);
                log.debug("Entity:" + entityClass.getSimpleName() + " set to:" + entity);
                return entity;
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        entity = null;
        log.debug("Entity:" + entityClass.getSimpleName() + " by id:" + id + " not found");
        return entity;
    }
}
