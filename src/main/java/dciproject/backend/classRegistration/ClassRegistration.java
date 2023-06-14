package dciproject.backend.classRegistration;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public interface ClassRegistration {
    void setRegistrationNumber(int n);

    String getSubjectID();

    String getTLSN_APLY_DT();

    Integer getRegistrationNumber();
}
