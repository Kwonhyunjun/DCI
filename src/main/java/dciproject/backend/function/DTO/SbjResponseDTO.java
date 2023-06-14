package dciproject.backend.function.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SbjResponseDTO {
    public String sbjtNm; // 과목명
    public String shry; // 학년
    public String sbjtNo; // 과목번호
    public String dn ; // 개설학과
    public String divNm; // 이수구분
    public String profInfo ; // 담당교수
}
