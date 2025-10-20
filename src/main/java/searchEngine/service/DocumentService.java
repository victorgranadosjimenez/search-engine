package searchEngine.service;


import searchEngine.domain.Document;
import searchEngine.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    public Document save(Document doc) {
        Document saved = repository.save(doc);
        indexService.indexDocuments(List.of(saved));
        return saved;
    }

    public List<Document> findAll() {
        return repository.findAll();
    }

    public List<Document> search(String query) {
        Set<Long> ids = new HashSet<>();
        for (String word : query.toLowerCase().split("\\s+")) {
            ids.addAll(indexService.search(word));
        }
        return repository.findAllById(ids);
    }

    public Map<Document, Integer> searchWithRanking(String query) {
        List<Document> allDocs = repository.findAll();
        return indexService.searchRanked(query, allDocs);
    }
}

