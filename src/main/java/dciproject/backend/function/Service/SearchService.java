package dciproject.backend.function.Service;

import dciproject.backend.entireSubjects.entireSubject_2020.EntireSubject_2020;
import dciproject.backend.function.DTO.SbjRequestDTO;
import dciproject.backend.function.DTO.SbjResponseDTO;
import dciproject.backend.function.Repository.EntireSubjectRepository_2020;
import dciproject.backend.function.Repository.EntireSubjectRepository_2021;
import dciproject.backend.function.Repository.EntireSubjectRepository_2022;
import dciproject.backend.function.Repository.SubjectMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {
    private final EntireSubjectRepository_2020 subjectRepository_2020;
    private final EntireSubjectRepository_2021 subjectRepository_2021;
    private final EntireSubjectRepository_2022 subjectRepository_2022;

    public List<SubjectMapping> getSbjs (SbjRequestDTO sjRequestDTO){
        String year = sjRequestDTO.getYear();
        String shtm = sjRequestDTO.getShmt();
        String cdn = sjRequestDTO.getCdn();
        String colg = sjRequestDTO.getColg();
        String dn = sjRequestDTO.getDn();
        String keyword = sjRequestDTO.getKeyword();

        log.info("SearchSubjectController::: Year={}, Shmt={}, Cdn={}, Colg={}, Dn={}, Keyword={}",
                year,
                shtm,
                cdn,
                colg,
                dn,
                keyword
        );

        List<SubjectMapping> sbjResponseDTOList = switch (year){
            case "2020" ->
                subjectRepository_2020.findAllByShtmAndCptnDivNmAndDegrNmSustAndColgAndOpenSbjtNmContaining(shtm, cdn, dn, colg, keyword);
            case "2021"->
                subjectRepository_2021.findAllByShtmAndCptnDivNmAndDegrNmSustAndColgAndOpenSbjtNmContaining(shtm, cdn, dn, colg, keyword);
            case "2022"->
                subjectRepository_2022.findAllByShtmAndCptnDivNmAndDegrNmSustAndColgAndOpenSbjtNmContaining(shtm, cdn, dn, colg, keyword);
            default -> null;
        };

        return sbjResponseDTOList;
    }
}
