package dciproject.backend.entireSubjects.entireSubject_2022;

import dciproject.backend.entireSubjects.EntireSubject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntireSubject_2022 implements EntireSubject {
    @Id
    private String subjectID; // 년도+과목코드+분반

    @Column(name="OPEN_YR")
    private String openYr; // 강의 년도

    @Column(name="SHTM")
    private String shtm; // 강의 학기

    @Column(name = "TRGT_SHYR")
    private String trgtShyr; // 대상 학년

    @Column(name = "ORGN_CLSF_CD")
    private String orgnClsfCd; // 조직명(대학)

    @Column(name = "COLG")
    private String colg; // 단과대학명

    @Column(name = "DEGR_NM_SUST")
    private String degrNmSust; // 학과명

    @Column(name = "OPEN_SBJT_NO")
    private String openSbjtNo; // 과목 번호

    @Column(name = "OPEN_DCLSS")
    private String openDclss; // 분반 번호

    @Column(name = "OPEN_SBJT_NM")
    private String openSbjtNm; // 과목명

    @Column(name = "CPTN_DIV_NM")
    private String cptnDivNm; // 이수구분

    @Column(name = "PROF_INFO")
    private String profInfo; // 교수명

    @Column(name = "TMTBL_INFO", length = 1000)
    private String tmtblInfo; // 시간표
}
