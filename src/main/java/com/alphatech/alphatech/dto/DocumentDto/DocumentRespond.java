package com.alphatech.alphatech.dto.DocumentDto;

import com.alphatech.alphatech.dto.SuppliersDto.SupplierBasicInfo;
import com.alphatech.alphatech.model.DocumentContract;

import java.time.format.DateTimeFormatter;

public record DocumentRespond(
        Long Id,
        String documentName,
        String file,
        String fileType,
        String uploadDate,
        SupplierBasicInfo supplier,
        String description
) {
    public static DocumentRespond toDocumentRespond(DocumentContract document) {
        DateTimeFormatter uploadDateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
        return new DocumentRespond(
                document.getId(),
                document.getDocumentName(),
                document.getFile(),
                document.getFileType(),
                document.getUploadDate() != null ? document.getUploadDate().format(uploadDateformat) : null,
                document.getSupplier() != null
                        ? SupplierBasicInfo.fromSupplier(document.getSupplier())
                        : null,
                document.getDescription()
        );
    }
}
