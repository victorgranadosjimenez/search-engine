package searchEngine.controller;


import searchEngine.domain.Document;
import searchEngine.service.IndexService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @PostMapping
    public String indexDocuments(@RequestBody List<Document> documents) {
        indexService.indexDocuments(documents);
        return "Documentos indexados correctamente.";
    }

    @GetMapping
    public Map<String, Set<Long>> getIndex() {
        return indexService.getIndex();
    }
}
