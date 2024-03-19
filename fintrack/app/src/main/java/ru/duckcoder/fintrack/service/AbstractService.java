package ru.duckcoder.fintrack.service;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.model.AbstractModel;

import java.util.List;
import java.util.Map;

public abstract class AbstractService<
        D extends AbstractDTO,
        DC extends AbstractDTO,
        DU extends AbstractDTO,
        ID> implements AutoCloseable {
    @PersistenceUnit
    private static volatile EntityManagerFactory entityManagerFactory;

    protected AbstractService() {
        if (entityManagerFactory == null) {
            synchronized (AbstractService.class) {
                if (entityManagerFactory == null)
                    entityManagerFactory = Persistence.createEntityManagerFactory("FinTrackService");
            }
        }

    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager(Map<?, ?> map) {
        return entityManagerFactory.createEntityManager(map);
    }

    public EntityManager getEntityManager(SynchronizationType synchronizationType) {
        return entityManagerFactory.createEntityManager(synchronizationType);
    }

    public EntityManager getEntityManager(SynchronizationType synchronizationType, Map<?, ?> map) {
        return entityManagerFactory.createEntityManager(synchronizationType, map);
    }

    abstract D create(DC dto);
    abstract List<D> read();
    abstract D read(ID id);
    abstract D update(ID id,DU dto);
    abstract void delete(ID id);

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
