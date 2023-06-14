package dciproject.backend.keywordSet;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class KeywordSetController {
    private final KeywordSetService keywordSetService;

    public KeywordSetController(KeywordSetService keywordSetService) {
        this.keywordSetService = keywordSetService;
    }


//    @PostMapping("/keyword/save")
//    private void save(@RequestBody String keyword){
//        keywordSetService.saveKeyword(keyword);
//    }


    @GetMapping("/keyword/rank") // rank 까지의 리스트를 출력 order==1 ASC, else DESC
    private List<KeywordSet> readByRank(@RequestParam int rank, @RequestParam int sortingOrder){
        return keywordSetService.getRankedList(rank, sortingOrder);
    }
}
