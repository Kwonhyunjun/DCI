package dciproject.backend.classRegistration;

import dciproject.backend.classRegistration.classRegistration_2020.ClassRegistrationPK_2020;
import dciproject.backend.classRegistration.classRegistration_2020.ClassRegistration_2020;
import dciproject.backend.classRegistration.classRegistration_2021.ClassRegistrationPK_2021;
import dciproject.backend.classRegistration.classRegistration_2021.ClassRegistration_2021;
import dciproject.backend.classRegistration.classRegistration_2022.ClassRegistrationPK_2022;
import dciproject.backend.classRegistration.classRegistration_2022.ClassRegistration_2022;
import dciproject.backend.keywordSet.KeywordSet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassRegistrationService {
    private final EntityManager entityManager;
    public ClassRegistrationService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ClassRegistration> findAllByYear(int year){
        TypedQuery<ClassRegistration> query;

        query=switch (year){
            case 2020 -> entityManager.createQuery("SELECT e FROM ClassRegistration_2020 e", ClassRegistration.class);
            case 2021 -> entityManager.createQuery("SELECT e FROM ClassRegistration_2021 e", ClassRegistration.class);
            case 2022 -> entityManager.createQuery("SELECT e FROM ClassRegistration_2022 e", ClassRegistration.class);
            default -> null;
        };

        return query.getResultList();
    }

    @Transactional
    public ClassRegistration save(ClassRegistration registration){
        entityManager.persist(registration);
        return registration;
    }

    public List<ClassRegistration> findBySubjectId(String givenSubjectID){
        String[] args=givenSubjectID.split("-");
        int year=Integer.parseInt(args[0]); // 년도 parse

        TypedQuery<ClassRegistration> query;
        String queryString;

        queryString=switch (year){
            case 2020 -> "SELECT e FROM ClassRegistration_2020 e WHERE e.subjectID=:subject";
            case 2021 -> "SELECT e FROM ClassRegistration_2021 e WHERE e.subjectID=:subject";
            case 2022 -> "SELECT e FROM ClassRegistration_2022 e WHERE e.subjectID=:subject";
            default -> null;
        };

        query= entityManager.createQuery(queryString, ClassRegistration.class);
        query.setParameter("subject", givenSubjectID);

        return query.getResultList();
    }

    public ClassRegistration findById(int year, String id, String time){
        return switch (year) {
            case 2020 ->
                    entityManager.find(ClassRegistration_2020.class, new ClassRegistrationPK_2020(id, time));
            case 2021 ->
                    entityManager.find(ClassRegistration_2021.class, new ClassRegistrationPK_2021(id, time));
            case 2022 ->
                    entityManager.find(ClassRegistration_2022.class, new ClassRegistrationPK_2022(id, time));
            default -> null;
        };
    }

    @Transactional
    public void modifyRegistrationNumberById(int year,String id,String time,int number){
        ClassRegistration found=findById(year,id,time);
        found.setRegistrationNumber(number);

        ClassRegistration merged=entityManager.merge(found);

        entityManager.persist(merged);
    }


}
