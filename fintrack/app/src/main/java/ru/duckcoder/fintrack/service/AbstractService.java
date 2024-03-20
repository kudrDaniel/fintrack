package ru.duckcoder.fintrack.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.SynchronizationType;
import ru.duckcoder.fintrack.dto.AbstractCreateDTO;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.dto.AbstractUpdateDTO;

import java.util.List;
import java.util.Map;

public abstract class AbstractService<
        D extends AbstractDTO,
        CD extends AbstractCreateDTO,
        UD extends AbstractUpdateDTO,
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

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    protected EntityManager getEntityManager(Map<?, ?> map) {
        return entityManagerFactory.createEntityManager(map);
    }

    protected EntityManager getEntityManager(SynchronizationType synchronizationType) {
        return entityManagerFactory.createEntityManager(synchronizationType);
    }

    protected EntityManager getEntityManager(SynchronizationType synchronizationType, Map<?, ?> map) {
        return entityManagerFactory.createEntityManager(synchronizationType, map);
    }

    protected abstract D create(CD dto);
    protected abstract List<D> readAll();
    protected abstract D read(ID id);
    protected abstract D update(ID id, UD dto);
    protected abstract void delete(ID id);

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
