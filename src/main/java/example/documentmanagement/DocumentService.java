package com.example.documentmanagement;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DocumentService {
    private final Map<Long, Document> documents = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Document> getAllDocuments() {
        return new ArrayList<>(documents.values());
    }

    public Document getDocumentById(Long id) {
        return documents.get(id);
    }

    public Document createDocument(Document document) {
        Long id = idGenerator.getAndIncrement();
        document.setId(id);
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        documents.put(id, document);
        return document;
    }

    // Add more methods as needed, e.g., updateDocument, deleteDocument, etc.
}
