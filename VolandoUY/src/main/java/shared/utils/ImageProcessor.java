package shared.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ImageProcessor {

    public String uploadImage(File imageFile, String imagePath) {
        if (imageFile == null || !imageFile.exists()) {
            return null;
        }
        // process image
        processImage(imageFile);

        File output = new File("images", imagePath + ".jpg");
        output.getParentFile().mkdirs(); // Ensure directory exists
        try {
            Files.copy(imageFile.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return path: e.g. "images/filename.jpg"
        return output.getPath().replace("\\", "/");
    }

    // Process image to required format (e.g., resize, compress)
    // 100 x 100 pixels
    // Compress to reduce file size
    // .jpg
    private void processImage(File imageFile) {
        try {
            BufferedImage originalImage = ImageIO.read(imageFile);
            Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            ImageIO.write(resizedImage, "jpg", imageFile);
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
}
