package dciproject.backend.entireSubjects;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Data
@Component
public class EntireSubjectListStrategy {

    private final List<List<EntireSubject>> entireSubjectListInstance;
    private final HashMap<Integer,Integer> yearMapper=new HashMap<>();

    private List<EntireSubject> currentStrategy;

    public EntireSubjectListStrategy(List<List<EntireSubject>> classRegistrationListInstance) {
        this.entireSubjectListInstance = classRegistrationListInstance;
    }

    public void addStrategy(List<EntireSubject> newList, int year){
        yearMapper.put(year,count());
        entireSubjectListInstance.add(newList);
    }
    private int count(){
        return entireSubjectListInstance.size();
    }

    public void switchStrategy(int year){
        currentStrategy= getEntireSubjectListInstance().get(yearMapper.get(year));
    }

    public void setStrategy(List<EntireSubject> givenList){
        currentStrategy=givenList;
    }

    public int getStrategyIndex(){
        for(int i = 0; i< entireSubjectListInstance.size(); i++){
            if(entireSubjectListInstance.get(i).equals(getStrategy())){
                return i;
            }
        }
        return -1;
    }
    public List<EntireSubject> getStrategy(){
        return this.currentStrategy;
    }
}
