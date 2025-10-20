package searchEngine.service;


import searchEngine.domain.Document;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IndexService {

    // Estructura del índice: palabra -> conjunto de IDs de documentos
    private final Map<String, Set<Long>> invertedIndex = new ConcurrentHashMap<>();



    // Metodo para limpiar y dividir el texto
    private List<String> tokenize(String text) {
        return Arrays.stream(text.toLowerCase()
                        .replaceAll("[^a-záéíóúüñ0-9 ]", " ") // elimina símbolos
                        .split("\\s+")) // separa por espacios
                .filter(word -> word.length() > 2) // evita palabras muy cortas
                .toList();
    }

    // Agregar documentos al índice
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


    // Mostrar el índice (para depuración)
    public Map<String, Set<Long>> getIndex() {
        return invertedIndex;
    }





    // Buscar documentos que contengan una palabra
    public Set<Long> search(String word) {
        return invertedIndex.getOrDefault(word.toLowerCase(), Collections.emptySet());
    }



    //metodo para buscar con ranking
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

        // Ordenar por frecuencia (relevancia)
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




