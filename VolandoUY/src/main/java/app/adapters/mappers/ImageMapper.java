package app.adapters.mappers;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageMapper {

    /**
     * Convierte una cadena Base64 en un archivo temporal.
     *
     * @param base64 la imagen codificada en base64
     * @param suffix el sufijo del archivo, como ".jpg", ".png", ".tmp"
     * @return archivo temporal
     * @throws IOException si hay errores al crear el archivo
     */
    public static File fromBase64(String base64, String suffix) throws IOException {
        if (base64 == null || base64.isBlank()) return null;

        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        File tempFile = File.createTempFile("upload-", suffix);
        Files.write(tempFile.toPath(), decodedBytes);
        return tempFile;
    }

    /**
     * Convierte un archivo en una cadena Base64.
     *
     * @param file el archivo a convertir
     * @return base64 string
     * @throws IOException si hay errores de lectura
     */
    public static String toBase64(File file) throws IOException {
        if (file == null || !file.exists()) return null;

        byte[] bytes = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(bytes);
    }
}