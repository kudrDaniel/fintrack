package ru.duckcoder.fintrack;

import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.duckcoder.fintrack.model.User;

@Log4j2
public class SessionHandler {
    private static volatile SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        SessionFactory localFactory = sessionFactory;
        if (localFactory == null) {
            synchronized (SessionHandler.class) {
                localFactory = sessionFactory;
                if (localFactory == null) {
                    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
                    try {
                        sessionFactory = localFactory = new MetadataSources(registry)
                                .addAnnotatedClasses(User.class)
                                .buildMetadata()
                                .buildSessionFactory();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        StandardServiceRegistryBuilder.destroy(registry);
                    }
                }
            }
        }
        return localFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }
}
