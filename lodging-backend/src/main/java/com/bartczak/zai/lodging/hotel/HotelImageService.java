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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class HotelImageService {

    @Value("${hotel.image.dir:./hotel-images}")
    private String hotelImageDir;
    @Value("${hotel.image.size:300}")
    private int imageSize;

    public String save(String image) {
        try
        {
            val base64Image = image.split(",")[1];
            val imageBytes = Base64.getDecoder().decode(base64Image);
            val root = Path.of(hotelImageDir);
            createDirIfNotExists(root);

            val filename = UUID.randomUUID() + ".jpg";
            new FileOutputStream(root.resolve(filename).toString()).write(imageBytes);
            return filename;
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Failed to save image");
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

    public void delete(String imageName) {
        val file = Paths.get(hotelImageDir).resolve(imageName);
        if (file.toFile().exists()) {
            try {
                Files.delete(file);
            } catch (Exception e) {
                throw new RuntimeException("Could not delete the file: " + imageName);
            }
        }
    }
}
