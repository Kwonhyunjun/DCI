package dciproject.backend.entireSubjects;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Major {
    @Id
    String major;

    @Column
    String college;
}
