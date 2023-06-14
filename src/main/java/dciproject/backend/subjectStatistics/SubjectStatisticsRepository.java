package dciproject.backend.subjectStatistics;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStatisticsRepository extends JpaRepository<SubjectStatistics,String> {

}
