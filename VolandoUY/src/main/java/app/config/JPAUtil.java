package app.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            String host = ConfigProperties.get("db.host");
            String port = ConfigProperties.get("db.port");
            String dbName = ConfigProperties.get("db.name");
            String user = ConfigProperties.get("db.user");
            String password = ConfigProperties.get("db.password");
            System.out.println("DB host: " + ConfigProperties.get("db.host"));
            System.out.println("DB port: " + ConfigProperties.get("db.port"));
            System.out.println("DB name: " + ConfigProperties.get("db.name"));

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;

            Map<String, String> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", url);
            props.put("jakarta.persistence.jdbc.user", user);
            props.put("jakarta.persistence.jdbc.password", password);
            props.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");

            emf = Persistence.createEntityManagerFactory("VolandoApp", props);
            System.out.println("Conexión a DB: " + url);
        }
        return emf;
    }
}
