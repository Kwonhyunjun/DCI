package dciproject.backend.classRegistration;


import dciproject.backend.classRegistration.classRegistration_2020.ClassRegistration_2020;
import dciproject.backend.classRegistration.classRegistration_2021.ClassRegistration_2021;
import dciproject.backend.classRegistration.classRegistration_2022.ClassRegistration_2022;
import dciproject.backend.entireSubjects.EntireSubject;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Data
@Component
public class ClassRegistrationListStrategy {

    private final List<List<ClassRegistration>> classRegistrationListInstance;
    private final HashMap<Integer,Integer> yearMapper=new HashMap<>();
    private int count;

    private List<ClassRegistration> currentStrategy;

    public ClassRegistrationListStrategy(List<List<ClassRegistration>> classRegistrationListInstance) {
        this.classRegistrationListInstance = classRegistrationListInstance;
    }

    public void addStrategy(List<ClassRegistration> newList, int year){
        yearMapper.put(year,count());
        classRegistrationListInstance.add(newList);
    }
    private int count(){
        return classRegistrationListInstance.size();
    }

    public void switchStrategy(int year){
        currentStrategy=getClassRegistrationListInstance().get(yearMapper.get(year));
    }

    public void setStrategy(List<ClassRegistration> givenList){
        currentStrategy=givenList;
    }

    public int getStrategyIndex(){
        for(int i=0;i<classRegistrationListInstance.size();i++){
            if(classRegistrationListInstance.get(i).equals(getStrategy())){
                return i;
            }
        }
        return -1;
    }
    public List<ClassRegistration> getStrategy(){
        return this.currentStrategy;
    }
}
