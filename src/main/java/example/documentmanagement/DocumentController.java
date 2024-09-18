package com.example.documentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Document>>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        List<EntityModel<Document>> documentModels = documents.stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Document>> collectionModel = CollectionModel.of(documentModels,
                linkTo(methodOn(DocumentController.class).getAllDocuments()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Document>> getDocumentById(@PathVariable("id") Long id) {
        Document document = documentService.getDocumentById(id);
        EntityModel<Document> entityModel = toEntityModel(document);
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Document>> createDocument(@RequestBody Document document) {
        Document savedDocument = documentService.createDocument(document);
        EntityModel<Document> entityModel = toEntityModel(savedDocument);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    private EntityModel<Document> toEntityModel(Document document) {
        EntityModel<Document> entityModel = EntityModel.of(document);
        entityModel.add(linkTo(methodOn(DocumentController.class).getDocumentById(document.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(DocumentController.class).getAllDocuments()).withRel("documents"));
        return entityModel;
    }
}
