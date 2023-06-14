package dciproject.backend.classRegistration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ClassRegistrationController {
    private final ClassRegistrationService classRegistrationService;

    public ClassRegistrationController(ClassRegistrationService classRegistrationService) {
        this.classRegistrationService = classRegistrationService;
    }

    @GetMapping("/registration/read") // 하나의 과목에 대한 수강신청 정보 가져오기(List)
    private List<ClassRegistration> read(@RequestParam String subjectID){
        return classRegistrationService.findBySubjectId(subjectID);
    }

}
