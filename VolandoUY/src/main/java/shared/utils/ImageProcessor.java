package shared.utils;

import shared.constants.Images;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ImageProcessor {

    public static String uploadImage(File imageFile, String imagePath) {
        if (imageFile == null || imagePath == null) {
            return null;
        }

        if (!imageFile.exists()) {
            deleteImage(imagePath);
        }

        // process image
        processImage(imageFile);

        File output = new File(Images.IMAGES_PATH, imagePath);
        output.getParentFile().mkdirs(); // Ensure directory exists
        try {
            Files.copy(imageFile.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return path: e.g. "images/filename.jpg"
        System.out.println(output.getPath().replace("\\", "/"));
        return output.getPath().replace("\\", "/");
    }

    private static void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return;
        }
        File file = new File(imagePath);
        if (file.exists()) {
            file.delete();
        }
    }

    // Process image to required format (e.g., resize, compress)
    // 100 x 100 pixels
    // Compress to reduce file size
    // .jpg
    private static void processImage(File imageFile) {
        try {
            BufferedImage originalImage = ImageIO.read(imageFile);
            Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            ImageIO.write(resizedImage, Images.FORMAT_DEFAULT, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon makeRoundedIcon(String imagePath, int diameter) {
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return null;
        }
        Image srcImg;
        try {
            srcImg = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        BufferedImage mask = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = mask.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval(0, 0, diameter, diameter);
        g2.dispose();

        BufferedImage rounded = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2 = rounded.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter));
        g2.drawImage(srcImg, 0, 0, diameter, diameter, null);
        g2.dispose();

        return new ImageIcon(rounded);
    }

    public static String getImageAbsolutePath(String resourceName, String key) {
        String relativePath = getImageRelativePath(resourceName, key);
        if (relativePath == null) {
            relativePath = getDefaultImageRelativePath(resourceName);
        }

        // Get the file that is on IMAGES_PATH + relativePath
        File imageFile = new File(Images.IMAGES_PATH, relativePath);
        if (!imageFile.exists()) {
            return null;
        }

        return imageFile.getAbsolutePath();
    }

    private static String getImageRelativePath(String resourceName, String key) {

        if (resourceName == null || key == null) {
            return null;
        }

        if (resourceName.toLowerCase().contains("customer")) {
            return Images.CUSTOMERS_PATH + key + Images.FORMAT_DEFAULT;
        } else if (resourceName.toLowerCase().contains("airline")) {
            return Images.AIRLINES_PATH + key + Images.FORMAT_DEFAULT;
        } else if (resourceName.toLowerCase().contains("flightroutepackage")) {
            return Images.FLIGHT_ROUTES_PACKAGES_PATH + key + Images.FORMAT_DEFAULT;
        } else if (resourceName.toLowerCase().contains("flightroute")) {
            return Images.FLIGHT_ROUTES_PATH + key + Images.FORMAT_DEFAULT;
        } else if (resourceName.toLowerCase().contains("flight")) {
            return Images.FLIGHTS_PATH + key + Images.FORMAT_DEFAULT;
        } else {
            return null;
        }
    }

    private static String getDefaultImageRelativePath(String resourceName) {
        if (resourceName == null) {
            return Images.ERROR_IMAGE_PATH;
        }

        if (resourceName.toLowerCase().contains("customer")) {
            return Images.USER_DEFAULT;
        } else if (resourceName.toLowerCase().contains("airline")) {
            return Images.USER_DEFAULT;
        } else if (resourceName.toLowerCase().contains("flightroutepackage")) {
            return Images.FLIGHT_ROUTE_PACKAGE_DEFAULT;
        } else if (resourceName.toLowerCase().contains("flightroute")) {
            return Images.FLIGHT_ROUTE_DEFAULT;
        } else if (resourceName.toLowerCase().contains("flight")) {
            return Images.FLIGHT_DEFAULT;
        } else {
            return Images.ERROR_IMAGE_PATH;
        }
    }
}
