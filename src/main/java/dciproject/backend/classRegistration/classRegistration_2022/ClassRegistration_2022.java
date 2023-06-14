package dciproject.backend.classRegistration.classRegistration_2022;

import dciproject.backend.classRegistration.ClassRegistration;
import dciproject.backend.entireSubjects.entireSubject_2022.EntireSubject_2022;
import jakarta.persistence.*;
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
@IdClass(ClassRegistrationPK_2022.class)
public class ClassRegistration_2022 implements ClassRegistration {

    @Id
    private String subjectID;

    @Id
    private String TLSN_APLY_DT;

    @Column
    private Integer registrationNumber;

    @Override
    public void setRegistrationNumber(int n){
        registrationNumber=n;
    }
}
