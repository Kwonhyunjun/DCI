package dciproject.backend;


import dciproject.backend.classRegistration.ClassRegistration;
import dciproject.backend.classRegistration.ClassRegistrationListStrategy;
import dciproject.backend.classRegistration.ClassRegistrationService;
import dciproject.backend.entireSubjects.EntireSubjectListStrategy;
import dciproject.backend.entireSubjects.EntireSubjectService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SingleToneResources {
    public static ClassRegistrationListStrategy classRegistrationListStrategy;
    public static EntireSubjectListStrategy entireSubjectListStrategy;
    private final ClassRegistrationService classRegistrationService;
    private final EntireSubjectService entireSubjectService;
    SingleToneResources(ClassRegistrationListStrategy classStrategy,
                        EntireSubjectListStrategy subjectStrategy,
                        ClassRegistrationService classRegistrationService, EntireSubjectService entireSubjectService){

        classRegistrationListStrategy=classStrategy;
        entireSubjectListStrategy=subjectStrategy;
        this.classRegistrationService = classRegistrationService;
        this.entireSubjectService = entireSubjectService;

        initClassRegistrationListStrategy();
        initEntireSubjectListStrategy();
    }

    public void initClassRegistrationListStrategy() {
        classRegistrationListStrategy.addStrategy(classRegistrationService.findAllByYear(2020), 2020);
        classRegistrationListStrategy.addStrategy(classRegistrationService.findAllByYear(2021), 2021);
        classRegistrationListStrategy.addStrategy(classRegistrationService.findAllByYear(2022), 2022);
    }

    public void initEntireSubjectListStrategy() {
        entireSubjectListStrategy.addStrategy(entireSubjectService.findAllByYear(2020), 2020);
        entireSubjectListStrategy.addStrategy(entireSubjectService.findAllByYear(2021), 2021);
        entireSubjectListStrategy.addStrategy(entireSubjectService.findAllByYear(2022), 2022);
    }

}
