package dciproject.backend.subjectStatistics;

import dciproject.backend.entireSubjects.EntireSubject;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SubjectStatistics {

    @Id
    private String subjectID; // 년도+과목코드+분반

    @Column
    private double comp_rate; // 선호

    @Column
    private int comp_level; // 선호도 수준

    @Column
    private double correctedRate; // 정정인원 비율

    @Column
    private Integer correctedNum; // 정정인원

    @Column
    private int totalNum; // 수강인원
}
