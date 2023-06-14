package dciproject.backend.keywordSet;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordSetService {
    private final KeywordSetRepository keywordSetRepository;
    private final EntityManager entityManager;
    public KeywordSetService(KeywordSetRepository keywordSetRepository, EntityManager entityManager) {
        this.keywordSetRepository = keywordSetRepository;
        this.entityManager = entityManager;
    }

    public void saveKeyword(String keyword){
        KeywordSet detachedData=keywordSetRepository.findById(keyword).orElse(null);
        if(detachedData!=null){
            detachedData.setNumber(detachedData.getNumber()+1);
            keywordSetRepository.save(detachedData); // 영속성 컨텍스트 분리 후 저장
        }else{
            keywordSetRepository.save(new KeywordSet(keyword,1));
        }
    }

    public List<KeywordSet> getRankedList(int rank,int order){
        List<KeywordSet> list=keywordSetRepository.findAll();

        if(order==0) // ASC
            list.sort((k1,k2)->k2.number-k1.number);
        else // DESC
            list.sort((k1,k2)->k1.number-k2.number);

        return list.subList(0, Math.min(list.size(), rank));
    }

}
