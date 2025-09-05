package utils;

import app.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TestUtils {
    public static void cleanDB() {
        EntityManager em = DBConnection.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createNativeQuery("TRUNCATE TABLE airline CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE airport CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE basicluggage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE bookflight CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE bookflight_ticket CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE buypackage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE buypackage_bookflight CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE category CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE city CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE customer CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE customer_bookflight CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE customer_buypackage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE extraluggage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flight CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flight_seat CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flightroute CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flightroute_category CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flightroutepackage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flightroutepackage_buypackage CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE flightroutepackage_flightroute CASCADE").executeUpdate();
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
