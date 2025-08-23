//package app.config;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class ConfigLoader {
//
//    private Properties properties = new Properties();
//
//    public ConfigLoader(String fileName) {
//        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
//            if (input == null) {
//                throw new RuntimeException("No se encontró el archivo de configuración: " + fileName);
//            }
//            properties.load(input);
//        } catch (IOException ex) {
//            throw new RuntimeException("Error al cargar el archivo de configuración", ex);
//        }
//    }
//
//    public String get(String key) {
//        return properties.getProperty(key);
//    }
//
//    public int getInt(String key) {
//        return Integer.parseInt(properties.getProperty(key));
//    }
//    public boolean getBoolean(String key) {
//        return Boolean.parseBoolean(properties.getProperty(key));
//    }
//}
