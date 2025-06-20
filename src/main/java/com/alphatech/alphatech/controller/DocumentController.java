package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.dto.DocumentDto.DocumentRequest;
import com.alphatech.alphatech.dto.DocumentDto.DocumentRespond;
import com.alphatech.alphatech.service.IDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contractDoc")
@RequiredArgsConstructor
public class DocumentController {
    private final IDocumentService documentService;

    @GetMapping
    public ResponseEntity<?> getAllDocuments() {
        try{
            List<DocumentRespond> documentResponds = documentService.getAllDocuments();
            if (documentResponds.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No documents found.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(documentResponds);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentRespond> updateDocument(
            @RequestParam("documentName") final String documentName,
            @RequestParam("file") final MultipartFile file,
            @RequestParam(value = "fileType", required = false) final String fileType,
            @RequestParam(value = "description", required = false) final String description,
            @RequestParam("supplierId") final Long supplierId
    ) throws Exception {

        DocumentRequest request = new DocumentRequest(documentName, file, fileType, supplierId , description);
        DocumentRespond response = documentService.createDocument(request);

        return ResponseEntity.ok(response);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentRespond> updateDocument(
            @PathVariable("id") final Long documentId,
            @RequestParam("documentName") final String documentName,
            @RequestParam(value = "file", required = false) final MultipartFile file,
            @RequestParam(value = "fileType", required = false) final String fileType,
            @RequestParam(value = "description", required = false) final String description,
            @RequestParam("supplierId") final Long supplierId
    ) throws Exception {

        DocumentRequest request = new DocumentRequest(documentName, file, fileType, supplierId, description);
        DocumentRespond updatedResponse = documentService.updateDocument(documentId, request);
        return ResponseEntity.ok(updatedResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("id") final Long id) throws IOException {
        documentService.deleteDocument(id);
        return ResponseEntity.status(HttpStatus.OK).body("Document deleted successfully.");
    }
}
