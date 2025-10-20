package searchEngine.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import searchEngine.domain.Document;
import searchEngine.repository.DocumentRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IndexService {

    private final DocumentRepository documentRepository;
    private final Map<String, Set<Long>> invertedIndex = new ConcurrentHashMap<>();

    public IndexService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @PostConstruct
    public void init() {
        List<Document> allDocs = documentRepository.findAll();
        indexDocuments(allDocs);
        System.out.println("✅ Índice cargado con " + allDocs.size() + " documentos desde la BD");
    }

    public void reindexAll() {
        invertedIndex.clear();
        List<Document> allDocs = documentRepository.findAll();
        indexDocuments(allDocs);
    }

    private List<String> tokenize(String text) {
        return Arrays.stream(text.toLowerCase()
                        .replaceAll("[^a-záéíóúüñ0-9 ]", " ")
                        .split("\\s+"))
                .filter(word -> word.length() > 2)
                .toList();
    }

    public void indexDocuments(List<Document> documents) {
        for (Document doc : documents) {
            String text = Optional.ofNullable(doc.getText()).orElse("");
            List<String> words = tokenize(text);
            for (String word : words) {
                invertedIndex
                        .computeIfAbsent(word, k -> new HashSet<>())
                        .add(doc.getId());
            }
        }
    }

    public Map<String, Set<Long>> getIndex() {
        return invertedIndex;
    }

    public Set<Long> search(String word) {
        return invertedIndex.getOrDefault(word.toLowerCase(), Collections.emptySet());
    }

    public Map<Document, Integer> searchRanked(String query, List<Document> allDocuments) {
        Map<Document, Integer> rankedResults = new HashMap<>();
        List<String> words = tokenize(query);

        for (String word : words) {
            Set<Long> docIds = invertedIndex.getOrDefault(word, Collections.emptySet());
            for (Long id : docIds) {
                allDocuments.stream()
                        .filter(d -> d.getId().equals(id))
                        .findFirst()
                        .ifPresent(doc -> {
                            int score = rankedResults.getOrDefault(doc, 0);
                            rankedResults.put(doc, score + 1);
                        });
            }
        }

        return rankedResults.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .collect(
                        LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll
                );
    }
}
