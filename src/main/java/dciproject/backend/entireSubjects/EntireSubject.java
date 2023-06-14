package dciproject.backend.entireSubjects;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public interface EntireSubject {
    String getSubjectID(); // 년도+과목코드+분반

    String getOpenYr(); // 강의 년도

    String getShtm(); // 강의 학기

    String getTrgtShyr(); // 대상 학년
    String getOrgnClsfCd(); // 조직명(대학)
    String getColg(); // 단과대학명
    String getDegrNmSust(); // 학과명

    String getOpenSbjtNo(); // 과목 번호

    String getOpenDclss(); // 분반 번호

    String getOpenSbjtNm(); // 과목명

    String getCptnDivNm(); // 이수구분

    String getProfInfo(); // 교수명

    String getTmtblInfo(); // 시간표

}
