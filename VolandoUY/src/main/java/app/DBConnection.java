package app;

import app.config.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class DBConnection {

    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = JPAUtil.getEntityManagerFactory();
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void cleanDB() {
        EntityManager em = getEntityManager();
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
            em.createNativeQuery("TRUNCATE TABLE follow CASCADE").executeUpdate();

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al limpiar la base de datos", e);
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
