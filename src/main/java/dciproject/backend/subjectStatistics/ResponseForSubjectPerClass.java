package dciproject.backend.subjectStatistics;


import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForSubjectPerClass {
    String subjectID;
    int corrected_num;
    int comp_level;
    double comp_rate;
}
