package searchEngine.service;


import searchEngine.domain.Document;
import searchEngine.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository repository;
    private final IndexService indexService;

    public DocumentService(DocumentRepository repository, IndexService indexService) {
        this.repository = repository;
        this.indexService = indexService;
    }

    // Guardar documento y actualizar índice
    public Document save(Document doc) {
        Document saved = repository.save(doc);
        indexService.indexDocuments(List.of(saved)); // indexa el nuevo documento
        return saved;
    }

    // Buscar documentos usando el índice invertido
    public List<Document> search(String query) {
        Set<Long> docIds = indexService.search(query);
        if (docIds.isEmpty()) return List.of();
        return repository.findAllById(docIds);
    }

    public List<Document> findAll() {
        return repository.findAll();
    }


    public Map<Document, Integer> searchWithRanking(String query) {
        List<Document> allDocs = repository.findAll();
        return indexService.searchRanked(query, allDocs);
    }

}
