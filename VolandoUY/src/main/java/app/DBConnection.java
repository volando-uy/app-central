package app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBConnection {

    private static final String ENVIRONMENT = System.getenv("ENVIRONMENT");

    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {

        String persistenceUnitName = ENVIRONMENT != null && ENVIRONMENT.equals("PROD") ? "VolandoAppProd" : "VolandoApp";

        if (persistenceUnitName.equals("VolandoAppProd")) {
            String dbUrl = System.getenv("DATABASE_URL");

            Pattern pattern = Pattern.compile("postgres://([^:]+):([^@]+)@([^:/]+):(\\d+)/(\\w+)");
            Matcher matcher = pattern.matcher(dbUrl);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("DATABASE_URL format is invalid");
            }

            String dbUser = matcher.group(1);
            String dbPassword = matcher.group(2);
            String dbHost = matcher.group(3);
            String dbPort = matcher.group(4);
            String dbName = matcher.group(5);

            Map<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.url", String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName));
            properties.put("jakarta.persistence.jdbc.user", dbUser);
            properties.put("jakarta.persistence.jdbc.password", dbPassword);
            properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
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
