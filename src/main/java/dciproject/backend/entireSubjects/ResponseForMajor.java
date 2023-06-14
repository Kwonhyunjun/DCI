package dciproject.backend.entireSubjects;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@AllArgsConstructor
@ToString
@Getter
public class ResponseForMajor {
    String college;
    List<String> majors;

    static ResponseForMajor toDto(String college,List<String> majors){
        return ResponseForMajor.builder().college(college).majors(majors).build();
    }
}
