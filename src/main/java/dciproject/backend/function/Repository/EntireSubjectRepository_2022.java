package dciproject.backend.function.Repository;

import dciproject.backend.entireSubjects.entireSubject_2022.EntireSubject_2022;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntireSubjectRepository_2022 extends JpaRepository<EntireSubject_2022, String> {
    List<SubjectMapping> findAllByShtmAndCptnDivNmAndDegrNmSustAndColgAndOpenSbjtNmContaining(String shtm, String cptnDivNm, String degrNmSust, String colg, String openSbjtNm);
}
