package ru.duckcoder.fintrack.backend.config;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.service.UserService;
import ru.duckcoder.fintrack.backend.service.CompanyService;
import ru.duckcoder.fintrack.backend.service.IndividualService;
import ru.duckcoder.fintrack.backend.service.PersonService;
import ru.duckcoder.fintrack.backend.service.def.UserServiceDef;
import ru.duckcoder.fintrack.backend.service.def.CompanyServiceDef;
import ru.duckcoder.fintrack.backend.service.def.IndividualServiceDef;
import ru.duckcoder.fintrack.backend.service.def.PersonServiceDef;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class DependencyProvider {
    private static volatile DependencyProvider instance;

    public static DependencyProvider getInstance() {
        DependencyProvider localInstance = instance;
        if (localInstance == null)
            synchronized (DependencyProvider.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new DependencyProvider();
            }
        return localInstance;
    }

    private final Map<Class<?>, Class<?>> classMap;

    private DependencyProvider() {
        this.classMap = new HashMap<>();
        this.addImplementation(UserService.class, UserServiceDef.class);
        this.addImplementation(PersonService.class, PersonServiceDef.class);
        this.addImplementation(CompanyService.class, CompanyServiceDef.class);
        this.addImplementation(IndividualService.class, IndividualServiceDef.class);
    }

    private <T> T castWithCheck(Object obj, Class<T> tClass) {
        return tClass.isInstance(obj) ? tClass.cast(obj) : null;
    }

    public <T> void addImplementation(Class<T> abstractClass, Class<? extends T> implementClass) {
        this.classMap.put(abstractClass, implementClass);
    }

    public <T> T getImplementation(Class<T> abstractClass) {
        if (!this.classMap.isEmpty()) {
            Class<?> implementClass = this.classMap.get(abstractClass);
            try {
                Object instance = implementClass
                        .getConstructor(EntityManager.class)
                        .newInstance(EntityManagerProvider.getInstance().getEntityManager());
                return this.castWithCheck(instance, abstractClass);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
