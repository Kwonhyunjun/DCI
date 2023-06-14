package dciproject.backend.classRegistration;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;


@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForClassRegistrationDto {
    String subjectID;
    private String TLSN_APLY_DT;
    private Integer registrationNumber;
}
