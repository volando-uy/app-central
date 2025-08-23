package app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBConnection {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("Volando");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
