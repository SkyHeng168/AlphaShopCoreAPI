package com.alphatech.alphatech.dto.DocumentDto;

import com.alphatech.alphatech.dto.SuppliersDto.SuppliersRespond;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record DocumentRequest(
        String documentName,
        MultipartFile file,
        String fileType,
        Long suppliersId,
        String description
) {
}
