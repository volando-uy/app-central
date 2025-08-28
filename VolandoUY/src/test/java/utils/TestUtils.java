package utils;

import app.DBConnection;
import jakarta.persistence.EntityManager;

public class TestUtils {
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
