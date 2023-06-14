package dciproject.backend.entireSubjects;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class EntireSubjectController {
    private final EntireSubjectService entireSubjectService;

    public EntireSubjectController(EntireSubjectService entireSubjectService) {
        this.entireSubjectService = entireSubjectService;
    }

    @GetMapping("/subject/load-majors")
    private List<ResponseForMajor> loadAllMajor(){
        HashMap<String, List<String>> map = entireSubjectService.loadMajorAll();
        List<ResponseForMajor> result=new ArrayList<>();

        map.forEach((k,v)->
            result.add(ResponseForMajor.builder().college(k).majors(v).build()));

        return result;
    }

    @PostMapping("/subject/save-major11101")
    private String saveMajor(){
        entireSubjectService.saveLists();
        return "OK";
    }
}
