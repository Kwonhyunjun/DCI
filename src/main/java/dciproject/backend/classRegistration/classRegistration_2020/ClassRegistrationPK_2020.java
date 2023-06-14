package dciproject.backend.classRegistration.classRegistration_2020;

import dciproject.backend.classRegistration.classRegistration_2022.ClassRegistrationPK_2022;
import dciproject.backend.entireSubjects.entireSubject_2020.EntireSubject_2020;
import dciproject.backend.entireSubjects.entireSubject_2022.EntireSubject_2022;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class ClassRegistrationPK_2020 implements Serializable {

    private String subjectID;
    private String TLSN_APLY_DT;
    @Override
    public int hashCode() {
        String[] args=subjectID.split("-");
        int v=0;
        for (String arg : args) v += Integer.parseInt(arg);
        return v+subjectID.hashCode()+TLSN_APLY_DT.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(((ClassRegistrationPK_2020)obj).subjectID.equals(this.subjectID)){
            return ((ClassRegistrationPK_2020) obj).TLSN_APLY_DT.equals(this.TLSN_APLY_DT);
        }
        return false;
    }
}
