package searchEngine.controller;

import org.springframework.http.ResponseEntity;
import searchEngine.domain.Document;
import searchEngine.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import searchEngine.service.IndexService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService service;
    private final IndexService indexService;

    public DocumentController(DocumentService service, IndexService indexService) {
        this.service = service;
        this.indexService = indexService;
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String q) {
        return service.search(q);
    }


    @PostMapping
    public Document save(@RequestBody Document doc) {
        Document saved = service.save(doc);      // 1. Guardar en la BD
        indexService.indexDocuments(List.of(saved)); // 2. Añadir al índice en memoria
        return saved;
    }


    @GetMapping
    public List<Document> all() {
        return service.findAll();
    }

    @GetMapping("/index")
    public Map<String, Set<Long>> index() {
        return indexService.getIndex();
    }


    @GetMapping("/search/ranked")
    public ResponseEntity<Map<Document, Integer>> searchRanked(@RequestParam String q) {
        List<Document> allDocuments = service.findAll(); // todos los documentos de la BD
        Map<Document, Integer> results = indexService.searchRanked(q, allDocuments);
        return ResponseEntity.ok(results);
    }



}

