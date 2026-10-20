package com.ecommercefml.product.service.impl;

import com.ecommercefml.product.expection.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    // Path for uploading
    @Value("${app.product.image.upload-dir}")
    private String uploadDir;

    @Override
    public Resource getImage(String fileName) throws IOException {
        // File path
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path path = uploadPath.resolve(fileName).normalize();

        Resource img = new UrlResource(path.toUri());

        // Check if its a file and it has permission to read it
        if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
            throw new ResourceNotFoundException("Image not found");
        }

        return img;
    }
}
