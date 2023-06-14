package dciproject.backend.function.Repository;

import dciproject.backend.entireSubjects.entireSubject_2020.EntireSubject_2020;
import dciproject.backend.entireSubjects.entireSubject_2021.EntireSubject_2021;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntireSubjectRepository_2021 extends JpaRepository<EntireSubject_2021, String> {
    List<SubjectMapping> findAllByShtmAndCptnDivNmAndDegrNmSustAndColgAndOpenSbjtNmContaining(String shtm, String cptnDivNm, String degrNmSust, String colg, String openSbjtNm);
}
