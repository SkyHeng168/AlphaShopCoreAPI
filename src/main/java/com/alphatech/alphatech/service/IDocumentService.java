package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.DocumentDto.DocumentRequest;
import com.alphatech.alphatech.dto.DocumentDto.DocumentRespond;

import java.io.IOException;
import java.util.List;

public interface IDocumentService {
    DocumentRespond createDocument(DocumentRequest documentRequest) throws IOException;
    List<DocumentRespond> getAllDocuments();
    DocumentRespond updateDocument(Long id, DocumentRequest documentRequest) throws IOException;
    void deleteDocument(Long id) throws IOException;
}
