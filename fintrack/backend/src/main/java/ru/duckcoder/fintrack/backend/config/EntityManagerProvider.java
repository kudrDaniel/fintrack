package ru.duckcoder.fintrack.backend.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.SynchronizationType;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class EntityManagerProvider {
    private static volatile EntityManagerProvider instance;

    public static EntityManagerProvider getInstance() {
        EntityManagerProvider localInstance = instance;
        if (localInstance == null) {
            synchronized (EntityManagerProvider.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new EntityManagerProvider();
            }
        }
        return localInstance;
    }

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    public EntityManagerProvider() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("FinTrackService");
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
}
