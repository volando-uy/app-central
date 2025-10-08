package app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private static final String ENVIRONMENT = System.getenv("ENVIRONMENT");

    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {

        String persistenceUnitName = ENVIRONMENT != null && ENVIRONMENT.equals("PROD") ? "VolandoAppProd" : "VolandoApp";

        if (ENVIRONMENT != null && ENVIRONMENT.equals("PROD")) {
            Map<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.url", System.getenv("DATABASE_URL"));
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
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();



            em.createNativeQuery("TRUNCATE TABLE airline CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE airport CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE basicluggage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE bookflight CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE buypackage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE category CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE city CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE customer CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE extraluggage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flight CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flightroute CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flightroute_category CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flightroutepackage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flight_route_package_join CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE seat CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE ticket CASCADE").executeUpdate();

            tx.commit(); // ✅ solo si todo fue bien
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback(); // ❌ si falló, deshacemos
            }
            throw new RuntimeException("Error al limpiar la base de datos", e);
        } finally {
            if (em.isOpen()) {
                em.close(); // 🔒 siempre cerramos el EM
            }
        }
    }
}
