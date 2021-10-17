package com.shopme.admin.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream fileStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException("Could not save file " + fileName, ex);
        }
    }

    public static void cleanDir(String dir) {
        Path dirPath = Path.of(dir);
        try {
            Files.list(dirPath).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException ex) {
                        System.err.println("Could not delete file " + file);
                    }
                }
            });
        } catch (IOException ignored) {
        }
    }
}
