package dciproject.backend.function.Controller;

import dciproject.backend.function.DTO.SbjRequestDTO;
import dciproject.backend.function.Repository.SubjectMapping;
import dciproject.backend.function.Service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search")
    public List<SubjectMapping> requestSearchSubject(@ModelAttribute SbjRequestDTO sbjRequestDTO){
        log.info("SearchSubjectController::: Year={}, Shmt={}, Cdn={}, Colg={}, Dn={}, Keyword={}",
                sbjRequestDTO.getYear(),
                sbjRequestDTO.getShmt(),
                sbjRequestDTO.getCdn(),
                sbjRequestDTO.getColg(),
                sbjRequestDTO.getDn(),
                sbjRequestDTO.getKeyword()
        );

        List<SubjectMapping> sbjResponse = searchService.getSbjs(sbjRequestDTO);

        return sbjResponse;
    }
}
