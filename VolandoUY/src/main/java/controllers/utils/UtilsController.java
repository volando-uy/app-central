package controllers.utils;

import lombok.AllArgsConstructor;
import shared.utils.ImageProcessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@AllArgsConstructor
public class UtilsController implements IUtilsController {

    ImageProcessor imageProcessor;

    @Override
    public String uploadImage(File imageFile, String imagePath) {
        return imageProcessor.uploadImage(imageFile, imagePath);
    }


}
