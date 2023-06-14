package dciproject.backend.keywordSet;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordSetRepository extends JpaRepository<KeywordSet,String> {}
