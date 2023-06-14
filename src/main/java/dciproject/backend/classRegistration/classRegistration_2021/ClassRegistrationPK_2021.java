package dciproject.backend.classRegistration.classRegistration_2021;

import dciproject.backend.classRegistration.classRegistration_2022.ClassRegistrationPK_2022;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class ClassRegistrationPK_2021 implements Serializable {

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
        if(((ClassRegistrationPK_2021)obj).subjectID.equals(this.subjectID)){
            return ((ClassRegistrationPK_2021) obj).TLSN_APLY_DT.equals(this.TLSN_APLY_DT);
        }
        return false;
    }
}
