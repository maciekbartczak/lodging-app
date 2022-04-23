package com.bartczak.zai.lodging.hotel;

import lombok.val;
import net.bytebuddy.matcher.ModifierMatcher;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class HotelImageService {

    @Value("${hotel.image.dir:./hotel-images}")
    private String hotelImageDir;
    @Value("${hotel.image.size:300}")
    private int imageSize;

    public String save(MultipartFile image) {
        try {
            val root = Path.of(hotelImageDir);
            createDirIfNotExists(root);

            var filename = image.getOriginalFilename();
            val extension = filename.substring(filename.lastIndexOf('.'));
            filename = UUID.randomUUID() + extension;

            val bufferedImage = ImageIO.read(image.getInputStream());
            val outputImage = Scalr.resize(bufferedImage, Scalr.Mode.FIT_TO_HEIGHT, imageSize);
            val outputFile = root.resolve(filename).toFile();

            ImageIO.write(outputImage, "png", outputFile);
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Could not save the file. " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            val file = Paths.get(hotelImageDir).resolve(filename);
            val resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not load the file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void createDirIfNotExists(Path root) {
        if (!root.toFile().exists()) {
            root.toFile().mkdirs();
        }
    }
}
