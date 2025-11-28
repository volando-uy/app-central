package app.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties properties = new Properties();

    static {
        String configDir = System.getProperty("config.home", System.getProperty("user.home") + File.separator + "volandouy");
        File externalConfig = new File(configDir, "application.properties");

        try {
            if (externalConfig.exists()) {
                try (FileInputStream fis = new FileInputStream(externalConfig)) {
                    properties.load(fis);
                    System.out.println("Configuración cargada desde: " + externalConfig.getAbsolutePath());
                }
            } else {
                try (InputStream input = ConfigProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
                    if (input != null) {
                        properties.load(input);
                        System.out.println("Configuración cargada desde el classpath interno");
                    } else {
                        System.err.println("No se encontró application.properties en ninguna ubicación");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}