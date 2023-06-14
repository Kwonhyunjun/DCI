package dciproject.backend.function.DTO;

import dciproject.backend.entireSubjects.entireSubject_2020.EntireSubject_2020;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SbjRequestDTO{
    public String year; // 년도
    public String shmt; // 학기
    public String cdn; // 이수구분
    public String colg; // 단과대
    public String dn ; // 학과
    public String keyword ; // 검색
}
