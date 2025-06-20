package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.DocumentDto.DocumentRequest;
import com.alphatech.alphatech.dto.DocumentDto.DocumentRespond;
import com.alphatech.alphatech.model.DocumentContract;
import com.alphatech.alphatech.model.Suppliers;
import com.alphatech.alphatech.repository.DocumentContractRepository;
import com.alphatech.alphatech.repository.SuppliersRepository;
import com.alphatech.alphatech.service.IDocumentService;
import com.alphatech.alphatech.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DocumentService implements IDocumentService {
    private final DocumentContractRepository documentContractRepository;
    private final SuppliersRepository suppliersRepository;

    @Override
    public DocumentRespond createDocument(DocumentRequest documentRequest) throws IOException {
        Suppliers suppliers = suppliersRepository.findById(documentRequest.suppliersId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found" + documentRequest.suppliersId()));
        String saveFileName = saveDocument(documentRequest.file());
        DocumentContract documentContract = DocumentContract.builder()
                .documentName(documentRequest.documentName())
                .file("uploads/contract/" + saveFileName)
                .fileType(documentRequest.fileType() != null ? documentRequest.fileType() : documentRequest.file().getOriginalFilename())
                .uploadDate(LocalDateTime.now())
                .supplier(suppliers)
                .description(documentRequest.description())
                .build();
        DocumentContract savedDocument = documentContractRepository.save(documentContract);
        return DocumentRespond.toDocumentRespond(savedDocument);
    }

    @Override
    public List<DocumentRespond> getAllDocuments() {
        List<DocumentContract> documentContracts = documentContractRepository.findAll();
        if (documentContracts.isEmpty()) {
            throw new ResourceNotFoundException("No documents found.");
        }
        return documentContracts.stream()
                .map(DocumentRespond::toDocumentRespond)
                .toList();
    }

    @Override
    public DocumentRespond updateDocument(Long id, DocumentRequest documentRequest) throws IOException {
        DocumentContract documentContract = documentContractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found."));
        Suppliers suppliers = suppliersRepository.findById(documentRequest.suppliersId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found."));
        if (documentRequest.file() != null && !documentRequest.file().isEmpty()) {
            String filename = saveDocument(documentRequest.file());
            documentContract.setFile("uploads/contract/" + filename);
            documentContract.setFileType(documentRequest.fileType() != null ? documentRequest.fileType() : documentRequest.file().getContentType());
        }
        documentContract.setDocumentName(documentRequest.documentName());
        documentContract.setDescription(documentRequest.description());
        documentContract.setSupplier(suppliers);
        documentContract.setUploadDate(LocalDateTime.now());

        DocumentContract savedDocument = documentContractRepository.save(documentContract);
        return DocumentRespond.toDocumentRespond(savedDocument);
    }

    @Override
    public void deleteDocument(Long id) throws IOException {
        DocumentContract documentContract = documentContractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found."));
        deleteDocument(documentContract.getFile());
        documentContractRepository.delete(documentContract);
    }

    private String saveDocument(MultipartFile file) throws IOException {
        return FileUploadUtil.saveFileDoc("uploads/contract", file);
    }
    private void deleteDocument(String file) throws IOException {
        try{
            Path path = Paths.get(file);
            if (Files.exists(path)){
                Files.delete(path);
            }
        }catch (IOException e){
            throw new IOException("Could not delete file " + file, e);
        }
    }
}
