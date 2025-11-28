package app.config;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties properties = new Properties();
    private static final String LOG_FILE = "config-error.log";

    static {
        // 1. Buscar en el directorio actual donde se ejecuta el .jar
        File currentDirConfig = new File("application.properties");

        // 2. Si no está, buscar en user.home/volandouy
        String configDir = System.getProperty("config.home", System.getProperty("user.home") + File.separator + "volandouy");
        File externalConfig = new File(configDir, "application.properties");

        try {
            if (currentDirConfig.exists()) {
                try (FileInputStream fis = new FileInputStream(currentDirConfig)) {
                    properties.load(fis);
                    System.out.println("Configuración cargada desde el directorio actual: " + currentDirConfig.getAbsolutePath());
                }
            } else if (externalConfig.exists()) {
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
                        logError("No se encontró application.properties en ninguna ubicación");
                    }
                }
            }
        } catch (Exception ex) {
            logException("Error cargando configuración", ex);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logWarning("Propiedad faltante: " + key);
        }
        return value;
    }

    public static String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        String value = properties.getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            logWarning("Error al convertir a int la propiedad: " + key + " = " + value);
            return 0;
        }
    }

    public static int getIntOrDefault(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key, "false"));
    }

    private static void logWarning(String message) {
        System.err.println("[WARNING] " + message);
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.printf("[%s] WARNING: %s%n", LocalDateTime.now(), message);
        } catch (IOException ignored) {
        }
    }

    private static void logError(String message) {
        System.err.println("[ERROR] " + message);
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.printf("[%s] ERROR: %s%n", LocalDateTime.now(), message);
        } catch (IOException ignored) {
        }
    }

    private static void logException(String context, Exception ex) {
        System.err.println("[ERROR] " + context);
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.printf("[%s] ERROR: %s%n", LocalDateTime.now(), context);
            ex.printStackTrace(out);
        } catch (IOException ignored) {
        }
    }
}
