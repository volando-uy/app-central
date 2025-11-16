package app.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("No se encontró application.properties");
            } else {
                properties.load(input);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
