package app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private static final String ENVIRONMENT = System.getenv("ENVIRONMENT");

    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {

        String persistenceUnitName = ENVIRONMENT != null && ENVIRONMENT.equals("TEST") ? "VolandoAppTest" : "VolandoApp";

        if (ENVIRONMENT != null && ENVIRONMENT.equals("PROD")) {
            Map<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));
            properties.put("jakarta.persistence.jdbc.url", System.getenv("DB_URL"));
            properties.put("jakarta.persistence.jdbc.user", System.getenv("DB_USER"));
            return Persistence.createEntityManagerFactory(persistenceUnitName, properties);
        } else {
            return Persistence.createEntityManagerFactory(persistenceUnitName);
        }
    }

    public static EntityManager getEntityManager() {
        if (emf == null) {
            emf = getEntityManagerFactory();
        }
        return emf.createEntityManager();
    }

    public static void cleanDB() {
        EntityManager em = DBConnection.getEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE airline CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE airport CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE category CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE city CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE customer CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE flight CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE flightroute CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE flightroute_category CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE flightroutepackage CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE flightroutepackage_flightroute CASCADE").executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}
