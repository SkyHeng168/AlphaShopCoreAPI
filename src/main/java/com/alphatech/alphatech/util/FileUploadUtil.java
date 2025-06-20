package com.alphatech.alphatech.util;

import com.alphatech.alphatech.Exception.customException.FileStorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {

    public static String saveFileDoc(String uploadDir, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("File is empty or missing.");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new FileStorageException("Original filename is invalid.");
        }

        String cleanFileName = Path.of(originalFileName).getFileName().toString();
        String uniqueFileName = UUID.randomUUID() + "-" + cleanFileName;

        Path uploadPath = Paths.get(uploadDir);
        if (Files.notExists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public static String saveFile(String uploadDir, MultipartFile file) throws IOException, IOException {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }
}
